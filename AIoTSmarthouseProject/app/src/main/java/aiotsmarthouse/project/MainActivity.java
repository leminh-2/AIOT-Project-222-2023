package aiotsmarthouse.project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    MQTTHelper mqttHelper;
    TextView txtTemp, txtHumi, txtLight;
    LabeledSwitch switch1stfloor, switch2ndfloor, switchAllLight, switchstairlight, switchDoor,switchSecurity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Begin full screen app
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //end full screen app

        setContentView(R.layout.activity_main);

        txtTemp = findViewById(R.id.txtTemp);
        txtHumi = findViewById(R.id.txtHumidity);
        txtLight = findViewById(R.id.txtLight);
        switch1stfloor = findViewById(R.id.switch1stfloor);
        switch2ndfloor = findViewById(R.id.switch2ndfloor);
        switchAllLight = findViewById(R.id.switchAllLight);
        switchstairlight = findViewById(R.id.switchstairlight);
        switchDoor = findViewById(R.id.switchDoor);
        switchSecurity = findViewById(R.id.switchSecurity);

        switch1stfloor.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("lygiahuy05022002/feeds/relay1", "1");
                }
                else sendDataMQTT("lygiahuy05022002/feeds/relay1", "0");
            }
        });

        switch2ndfloor.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("lygiahuy05022002/feeds/relay2", "1");
                }
                else sendDataMQTT("lygiahuy05022002/feeds/relay2", "0");
            }
        });

        switchstairlight.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("lygiahuy05022002/feeds/relay3", "1");
                }
                else sendDataMQTT("lygiahuy05022002/feeds/relay3", "0");
            }
        });

        switchDoor.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("lygiahuy05022002/feeds/doorbutton", "1");
                }
                else sendDataMQTT("lygiahuy05022002/feeds/doorbutton", "0");
            }
        });

        switchSecurity.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("lygiahuy05022002/feeds/security", "1");
                }
                else sendDataMQTT("lygiahuy05022002/feeds/security", "0");
            }
        });

        switchAllLight.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("lygiahuy05022002/feeds/ledbutton", "1");
                }
                else sendDataMQTT("lygiahuy05022002/feeds/ledbutton", "0");
            }
        });
        startMQTT();
    }

    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try{
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch(MqttException e){
        }
    }
    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(topic.contains("temp")) txtTemp.setText(message.toString()+ "°C");
                else if(topic.contains("humi")) txtHumi.setText(message.toString() + "%");
                else if(topic.contains("light")) txtLight.setText(message.toString() + "%");
                else if(topic.contains("doorbutton")) {
                    if(message.toString().equals("1")) switchDoor.setOn(true);
                    else switchDoor.setOn(false);
                }
                else if(topic.contains("ledbutton")) {
                    if(message.toString().equals("1")) switchAllLight.setOn(true);
                    else switchAllLight.setOn(false);
                }
                else if(topic.contains("relay1")) {
                    if(message.toString().equals("1")) switch1stfloor.setOn(true);
                    else switch1stfloor.setOn(false);
                }
                else if(topic.contains("relay2")) {
                    if(message.toString().equals("1")) switch2ndfloor.setOn(true);
                    else switch2ndfloor.setOn(false);
                }
                else if(topic.contains("relay3")) {
                    if(message.toString().equals("1")) switchstairlight.setOn(true);
                    else switchstairlight.setOn(false);
                }
                else if(topic.contains("security")) {
                    if(message.toString().equals("1")) switchSecurity.setOn(true);
                    else switchSecurity.setOn(false);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}
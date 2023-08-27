# AIot Smart House Model using Yolo:bit
This project is an AIot (Artificial Intelligence of Things) smart house model using Yolo:bit. The model includes sensors connected via serial to a Python gateway, which then connects to the IoT Adafruit server using MQTT. Additionally, an Android app has been developed using Android Studio, which also connects to the Adafruit server via MQTT.

## Hardware Requirements
(Read pdf file for more detail)

## Software Requirements
- Python 3.x
- Android Studio
- Adafruit server
- Yolo:bit code 

# Installation
## Python Gateway
1. Clone the repository.
2. Install the required libraries: adafruit-io, pyserial
3. Connect the sensors to the Yolo:bit board via serial.
4. Run the Python gateway: python gateway.py.
## Android App
1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on your device or emulator.
## Yolo:bit program
1. Go to https://app.ohstem.vn/
2. Import file "SMARTER THAN YOUR HOME v2"
3. Run the code

# Configuration
## Python Gateway
Edit the main.py file to configure the MQTT connection settings.
## Android App
Edit the MQTTHelper.java file to configure the MQTT connection settings.

# Usage
## Python Gateway
- The gateway will read data from the sensors and send it to the Adafruit server via MQTT.
## Android App
- The app will display the data from the sensors in real time.

# Contributors
- Ly Gia Huy (Leader) - 2053038
- Tran Pham Minh Dang - 2052070
- Le Quang Minh - 2053217
- Nguyen Quang Vinh - 2053591

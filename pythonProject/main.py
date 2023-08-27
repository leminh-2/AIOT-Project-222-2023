import sys
from Adafruit_IO import MQTTClient
import time
import random
from uart import *

AIO_FEED_ID = ["temp", "humi", "light", "relay1", "relay2", "relay3", "ledbutton", "security", "doorbutton"]
AIO_USERNAME = "lygiahuy05022002"
AIO_KEY = "key_kHoz82rC78CkW8BT3G9jf3LXAoDy"
AIO_KEY = AIO_KEY.replace(AIO_KEY[:3], "aio")

def connected(client):
    print("Ket noi thanh cong ...")
    for topic in AIO_FEED_ID:
        client.subscribe(topic)

def subscribe(client , userdata , mid , granted_qos):
    print("Subscribe thanh cong ...")

def disconnected(client):
    print("Ngat ket noi ...")
    sys.exit (1)

def message(client , feed_id , payload):
    print("Nhan du lieu: " + payload + " , feed id:", feed_id)
    if feed_id == "doorbutton":
        if payload == "1":
            writeData("1")
        else:
            writeData("2")
    if feed_id == "ledbutton":
        if payload == "1":
            writeData("3")
        else:
            writeData("4")
    if feed_id == "relay1":
        if payload == "1":
            writeData("5")
        else:
            writeData("6")
    if feed_id == "relay2":
        if payload == "1":
            writeData("7")
        else:
            writeData("8")
    if feed_id == "relay3":
        if payload == "1":
            writeData("9")
        else:
            writeData("10")
    if feed_id == "security":
        if payload == "1":
            writeData("11")
        else:
            writeData("12")

client = MQTTClient(AIO_USERNAME , AIO_KEY)
client.on_connect = connected
client.on_disconnect = disconnected
client.on_message = message
client.on_subscribe = subscribe
client.connect()
client.loop_background()

#main program
counter_updateValue = 1
counter_readSever = 1

readSerial(client)
while True:
    counter_updateValue = counter_updateValue - 1
    if counter_updateValue <= 0:
        counter_updateValue = 1
        #TODO1 - adding sensor value to server
        readSerial(client)
    time.sleep(1)
    pass
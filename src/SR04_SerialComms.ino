//www.elegoo.com
//2016.12.08
#include "SR04.h"

#define TRIG_PIN 12
#define ECHO_PIN 11 

SR04 sr04 = SR04(ECHO_PIN,TRIG_PIN);
long a;

String inputString = "";         // a String to hold incoming data
bool stringComplete = false;  // whether the string is complete

void setup() {
   inputString.reserve(200);
   Serial.begin(9600);//Initialization of Serial Port
   //Serial.setTimeout(60*60*1000ul);
    while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
}

void loop() {
  if (stringComplete) {
    // clear the string:
    if(inputString=="uno\n"){
      Serial.println("uno");
    }
    if(inputString=="r\n"){
        inputString = "";
        a=sr04.Distance();
        String CommStr = "cm";
        CommStr = a+CommStr;
        Serial.println(CommStr);
    }
    stringComplete = false;
    inputString = "";
  }
}

void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    // add it to the inputString:
    inputString += inChar;
    // if the incoming character is a newline, set a flag so the main loop can
    // do something about it:
    if (inChar == '\n') {
      stringComplete = true;
    }
  }
}

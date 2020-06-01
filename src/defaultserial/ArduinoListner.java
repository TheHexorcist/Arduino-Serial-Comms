package defaultserial;

import java.nio.charset.StandardCharsets;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

public class ArduinoListner implements SerialPortMessageListener {
	

	private String strArduino = "";
	private boolean IsArduino = false;

	@Override
	public boolean delimiterIndicatesEndOfMessage() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public byte[] getMessageDelimiter() {
		// TODO Auto-generated method stub
		return "\n".getBytes();
	}
	
	@Override
	public int getListeningEvents() {
		// TODO Auto-generated method stub
		 return SerialPort.LISTENING_EVENT_DATA_RECEIVED; 
	}

	@Override
	public void serialEvent(SerialPortEvent EventcomPort) {
		// TODO Auto-generated method stub

		byte[] readBuffer = EventcomPort.getReceivedData();
		strArduino = new String(readBuffer, StandardCharsets.UTF_8);
		if(strArduino=="uno")
			IsArduino = true;
		
	}
	
	public boolean IsitArduino() {
		return IsArduino;
	}
	
	public String GetArduinoResponse() {
		return strArduino;
	}
	
	/*private static SerialPort searchPorts(SerialPort[] comPorts) {
		String portName = "";
		SerialPort serialPort = null;
		
		 for (SerialPort i : comPorts) {
			 portName = i.getDescriptivePortName();
			 if(portName.contains("Arduino Uno")) {
				serialPort = i;
				break;
			 }
		 }
		 return serialPort;

	}*/

}

package defaultserial;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;

public class RxTxForm extends JFrame implements KeyListener, WindowListener, SerialPortMessageListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static SerialPort[] comPorts;
	private static SerialPort comPort=null;
	private static JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RxTxForm frame = new RxTxForm();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
					StringWriter stackTraceWriter = new StringWriter();
					e.printStackTrace(new PrintWriter(stackTraceWriter));
					textArea.append(e.toString() + "\n" + stackTraceWriter.toString());
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RxTxForm() {
		setTitle("RxTx Communication");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setEditable(false);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(textArea, BorderLayout.CENTER);
		
		JScrollPane JP = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		contentPane.add(JP);
		
		addKeyListener(this);
		addWindowListener(this);
		
		searchPorts();
		
		if (comPort != null) {
			comPort.openPort();		
			comPort.addDataListener(this);
		} else {
			removeKeyListener(this);
			removeWindowListener(this);
			textArea.append("connection error\n");
			textArea.append("no Arduino Uno device found:\n");
			textArea.append("insure Arduino USB drivers are installed\n");
		}
	}

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

		try {
			byte[] readBuffer = EventcomPort.getReceivedData();
			String strreadBuffer="";
			strreadBuffer = new String(readBuffer, "UTF-8");
			textArea.append(strreadBuffer);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter stackTraceWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stackTraceWriter));
			textArea.append(e.toString() + "\n" + stackTraceWriter.toString());
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if((char)'r'==e.getKeyChar())
			comPort.writeBytes("r\n".getBytes(), "r\n".length());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		comPort.removeDataListener();
		comPort.closePort();
		removeKeyListener(this);
		removeWindowListener(this);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void searchPorts() {
		comPorts = SerialPort.getCommPorts();
		
		for (SerialPort i : comPorts) {
			if (i.getDescriptivePortName().contains("Arduino Uno")) {
				comPort = i;
			}			
		}
		
	}
	
}

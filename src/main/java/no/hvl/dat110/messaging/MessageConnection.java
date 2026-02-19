package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import no.hvl.dat110.TODO;


public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {

		byte[] data;
		

		try {
			// encapsulate the data contained in the Message and write to the output stream
			byte[] segment = MessageUtils.encapsulate(message);
			outStream.write(segment);
			outStream.flush();
		} catch (IOException ex) {
			System.out.println("Connection send: " + ex.getMessage());
			ex.printStackTrace();
		}


	}

	public Message receive() {

		Message message = null;
		byte[] data;
		
		try {
			// read a full segment from the input stream (128 bytes)
			byte[] segment = new byte[MessageUtils.SEGMENTSIZE];
			inStream.readFully(segment);

			// decapsulate segment into a Message
			message = MessageUtils.decapsulate(segment);

		} catch (IOException ex) {
			System.out.println("Connection receive: " + ex.getMessage());
			ex.printStackTrace();
		}
		
		return message;
		
	}

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
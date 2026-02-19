package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 9090;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {
		
		if (message == null) throw new IllegalArgumentException("message is null");

		byte[] data = message.getData();
		if (data == null) data = new byte[0];
		if (data.length > SEGMENTSIZE - 1)
			throw new IllegalArgumentException("payload too large (max 127 bytes)");

		byte[] segment = new byte[SEGMENTSIZE];

		// first byte = length (0..127)
		segment[0] = (byte) data.length;

		// copy payload into segment starting at index 1
		System.arraycopy(data, 0, segment, 1, data.length);
		
		return segment;
		
	}

	public static Message decapsulate(byte[] segment) {

		if (segment == null) throw new IllegalArgumentException("segment is null");
		if (segment.length != SEGMENTSIZE)
			throw new IllegalArgumentException("segment must be exactly " + SEGMENTSIZE + " bytes");

		// segment[0] is signed byte in Java; convert to 0..255 using & 0xFF
		int len = segment[0] & 0xFF;
		if (len > SEGMENTSIZE - 1)
			throw new IllegalArgumentException("invalid payload length: " + len);

		byte[] data = Arrays.copyOfRange(segment, 1, 1 + len);

		return new Message(data);
		
	}
	
}

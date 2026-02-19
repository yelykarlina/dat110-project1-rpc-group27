package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import no.hvl.dat110.TODO;

public class RPCUtils {
	
	public static byte[] encapsulate(byte rpcid, byte[] payload) {
		
		if (payload == null) payload = new byte[0];

		byte[] rpcmsg = new byte[1 + payload.length];

		// first byte is rpcid
		rpcmsg[0] = rpcid;

		// remaining bytes are payload
		System.arraycopy(payload, 0, rpcmsg, 1, payload.length);
		
		return rpcmsg;
	}
	
	public static byte[] decapsulate(byte[] rpcmsg) {
		
		if (rpcmsg == null || rpcmsg.length < 1) {
			return new byte[0];
		}

		// payload is everything after rpcid
		return Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length);
		
	}

	// convert String to byte array
	public static byte[] marshallString(String str) {
		
		if (str == null) str = "";
		return str.getBytes(StandardCharsets.UTF_8);
	}


	// convert byte array to a String
	public static String unmarshallString(byte[] data) {
		
		if (data == null) return "";
		return new String(data, StandardCharsets.UTF_8);
	}
	
	public static byte[] marshallVoid() {
		
		return new byte[0];
		
	}
	
	public static void unmarshallVoid(byte[] data) {
		
		// TODO
		
	}

	// convert boolean to a byte array representation
	public static byte[] marshallBoolean(boolean b) {
		
		byte[] encoded = new byte[1];
				
		if (b) {
			encoded[0] = 1;
		} else
		{
			encoded[0] = 0;
		}
		
		return encoded;
	}

	// convert byte array to a boolean representation
	public static boolean unmarshallBoolean(byte[] data) {
		
		return (data[0] > 0);
		
	}

	// integer to byte array representation
	public static byte[] marshallInteger(int x) {
		

		return ByteBuffer.allocate(4).putInt(x).array();
	}
	
	// byte array representation to integer
	public static int unmarshallInteger(byte[] data) {
		
		if (data == null || data.length < 4) {
			throw new IllegalArgumentException("Invalid integer byte array");
		}

		return ByteBuffer.wrap(data).getInt();
		
	}
}

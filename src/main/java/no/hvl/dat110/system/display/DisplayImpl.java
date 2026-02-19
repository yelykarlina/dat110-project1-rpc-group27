package no.hvl.dat110.system.display;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCRemoteImpl;
import no.hvl.dat110.rpc.RPCUtils;
import no.hvl.dat110.rpc.RPCServer;

public class DisplayImpl extends RPCRemoteImpl {

	public DisplayImpl(byte rpcid, RPCServer rpcserver) {
		super(rpcid,rpcserver);
	}

	public void write(String message) {
		System.out.println("DISPLAY:" + message);
	}
	
	public byte[] invoke(byte[] param) {
		
		byte[] returnval = null;
		
		// TODO - START: 
		// implement unmarshalling
		String message = RPCUtils.unmarshallString(param);
		
		//call
		write(message);
		
		//and marshall for write RPC method
		returnval = RPCUtils.marshallVoid();
		
		// look at how this is done in the SensorImpl class for the read method
		
		// TODO - END
		
		return returnval;
	}
}

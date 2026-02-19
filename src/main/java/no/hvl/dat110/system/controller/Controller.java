package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;

public class Controller  {
	
	private static int N = 5;
	
	public static void main (String[] args) {
		
		DisplayStub display;
		SensorStub sensor;
		
		RPCClient displayclient,sensorclient;
		
		System.out.println("Controller starting ...");
				
		// create RPC clients for the system
		displayclient = new RPCClient(Common.DISPLAYHOST,Common.DISPLAYPORT);
		sensorclient = new RPCClient(Common.SENSORHOST,Common.SENSORPORT);
		
		// setup stop methods in the RPC middleware
		RPCClientStopStub stopdisplay = new RPCClientStopStub(displayclient);
		RPCClientStopStub stopsensor = new RPCClientStopStub(sensorclient);
				
		// TODO - START
		
		// give servers time to bind and start accepting
		try { Thread.sleep(300); } catch (InterruptedException e) { }

		// connect to sensor and display RPC servers - using the RPCClients
		displayclient.connect();
		sensorclient.connect();
		
		// create local display and sensor stub objects
		display = new DisplayStub(displayclient);
		sensor  = new SensorStub(sensorclient);
		
		// read value from sensor using RPC and write to display using RPC
		for (int i = 0; i < N; i++) {
			int value = sensor.read();
			display.write("Sensor=" + value);

			try { Thread.sleep(1000); } catch (InterruptedException e) { }
		}
		
		
		// TODO - END
		
		stopdisplay.stop();
		stopsensor.stop();
	
		displayclient.disconnect();
		sensorclient.disconnect();
		
		System.out.println("Controller stopping ...");
		
	}
}

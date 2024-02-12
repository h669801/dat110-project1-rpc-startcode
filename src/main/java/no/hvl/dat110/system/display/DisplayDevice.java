package no.hvl.dat110.system.display;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;


public class DisplayDevice {
	
	public static void main(String[] args) {
		
		System.out.println("Display server starting ...");
		
		// TODO - START
		// implement the operation of the display RPC server
		// see how this is done for the sensor RPC server in SensorDevice

		// Create a display RPC server
		RPCServer displayServer = new RPCServer(Common.DISPLAYPORT);

		// Register the display implementation
		DisplayImpl displayImpl = new DisplayImpl((byte) 1, displayServer);
		displayServer.register((byte) 1, displayImpl);

		// Start the display RPC server
		displayServer.run();
				
//		if (true)
//			throw new UnsupportedOperationException(TODO.method());
		
		// TODO - END
		
		System.out.println("Display server stopping ...");
		
	}
}

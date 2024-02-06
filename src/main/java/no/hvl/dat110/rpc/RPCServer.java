package no.hvl.dat110.rpc;

import java.io.IOException;
import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private MessageConnection connection;
	
	// hashmap to register RPC methods which are required to extend RPCRemoteImpl
	// the key in the hashmap is the RPC identifier of the method
	private HashMap<Byte,RPCRemoteImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Byte,RPCRemoteImpl>();
		
	}
	
	public void run() {
		
		// the stop RPC method is built into the server
		RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP,this);
		
		System.out.println("RPC SERVER RUN - Services: " + services.size());
			
		connection = msgserver.accept(); 
		
		System.out.println("RPC SERVER ACCEPTED");
		
		boolean stop = false;
		
		while (!stop) {
	    
		   byte rpcid = 0;
		   Message requestmsg, replymsg;
		   
		   // TODO - START
		   // - receive a Message containing an RPC request
		   // - extract the identifier for the RPC method to be invoked from the RPC request
		   // - extract the method's parameter by decapsulating using the RPCUtils
		   // - lookup the method to be invoked
		   // - invoke the method and pass the param
		   // - encapsulate return value 
		   // - send back the message containing the RPC reply

				try {
					// Receive a Message containing an RPC reques
					requestmsg = connection.receive();

					// Extract the identifier for the RPC method to be invoked from the RPC request
					rpcid = requestmsg.getData()[0];

					// Extract the method's parameter by decapsulating using the RPCUtils
					byte[] param = RPCUtils.decapsulate(requestmsg.getData());

					// Lookup the method to be invoked
					RPCRemoteImpl method = services.get(rpcid);

					if (method != null) {
						// Invoke the method and pass the param
						byte[] returnValue = method.invoke(param);

						// Encapsulate return value
						byte[] replydata = RPCUtils.encapsulate(rpcid, returnValue);

						// Send back the message containing the RPC reply
						replymsg = new Message(replydata);
						connection.send(replymsg);

						// Stop the server if it was stop method that was called
						if (rpcid == RPCCommon.RPIDSTOP) {
							stop = true;
						}
					} else {
						System.out.println("RPC method with id " + rpcid + " not found.");
					}

				} catch (IOException e) {
					// Handle IOException appropriately
					e.printStackTrace();
				}
//			}
			
//		   if (true)
//				throw new UnsupportedOperationException(TODO.method());
		   
		   // TODO - END

			// stop the server if it was stop methods that was called
		   if (rpcid == RPCCommon.RPIDSTOP) {
			   stop = true;
		   }
		}
	
	}
	
	// used by server side method implementations to register themselves in the RPC server
	public void register(byte rpcid, RPCRemoteImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {

		if (connection != null) {
			connection.close();
		} else {
			System.out.println("RPCServer.stop - connection was null");
		}
		
		if (msgserver != null) {
			msgserver.stop();
		} else {
			System.out.println("RPCServer.stop - msgserver was null");
		}
		
	}
}

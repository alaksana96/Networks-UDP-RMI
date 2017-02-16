/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException { //Constructor of the remote object
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		// TO-DO: On receipt of first message, initialise the receive buffer
		if(msg.messageNum == 1){
			receivedMessages = new int[msg.totalMessages];
		}
		// TO-DO: Log receipt of the message
		for(int i = 0; i < msg.totalMessages; i++){
			System.out.println(msg.toString());
			msg.messageNum++;
		}
		// TO-DO: If this is the last expected message, then identify
		//        any missing messages
		

	}


	public static void main(String[] args) {
		try {
			
			RMIServer rmis = null;
		
			// TO-DO: Initialise Security Manager
			if (System.getSecurityManager() == null) {
      			System.setSecurityManager(new SecurityManager());
			}	

			String urlServer = "RMIServer";

			// TO-DO: Instantiate the server class
			rmis = new RMIServer();
		
			// TO-DO: Bind to RMI registry
			rebindServer(urlServer, rmis);
			System.out.println("Server Ready");
			
		}
		catch (Exception e) {
		    System.out.println("RMIServer err: " + e.getMessage()); 
            e.printStackTrace(); 
		}

	}

	protected static void rebindServer(String serverURL, RMIServer server) {
		try{
			LocateRegistry.createRegistry(5099);
			Naming.bind(serverURL, server);
		}
		catch (Exception re){
			System.out.println("Exception: " + re.getMessage()); 
            re.printStackTrace(); 
		}
	}
}

/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import java.rmi.registry.Registry;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		// TO-DO: On receipt of first message, initialise the receive buffer

		// TO-DO: Log receipt of the message

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

		if(receivedMessages == null){
			totalMessages = msg.totalMessages;
			receivedMessages = new int[msg.totalMessages];
		}
		// TO-DO: Log receipt of the message
		receivedMessages[msg.messageNum] = 1;


		if (msg.messageNum + 1 == totalMessages) {
			System.out.println("Messages being totaled....");

			String lostmes = "Lost messages: ";
			int count = 0;
			for (int i = 0; i < totalMessages; i++) {
				if (receivedMessages[i] != 1) {
					count++;
					lostmes = lostmes + " " + (i+1) + ", ";
				}
			}

			if (count == 0) {
				lostmes = lostmes + "None";
			}
			System.out.println("Messages sent: " + totalMessages);
			System.out.println("Messages received: " + (totalMessages - count));
			System.out.println("Messages lost: " + count);
			System.out.println(lostmes);
			
			}





	}


	public static void main(String[] args) {

		System.setProperty("RMIServer","146.169.26.11");

		RMIServer rmis = null;

		// TO-DO: Initialise Security Manager
		if (System.getSecurityManager() == null) {
      		System.setSecurityManager(new SecurityManager());
		}

		try{
			rmis = new RMIServer();
			//RMIServerI stub = (RMIServerI) UnicastRemoteObject.exportObject(rmis, 0);
			
			rebindServer("RMIServer", rmis);

			System.out.println("Server Ready");
		}
		catch(Exception e){
            System.out.println("RMIServer err: " + e.getMessage());
            e.printStackTrace();
		}

		// TO-DO: Instantiate the server class

		// TO-DO: Bind to RMI registry

	}

	protected static void rebindServer(String serverURL, RMIServer server) {

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
		LocateRegistry.createRegistry(8080);
		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
		Naming.rebind(serverURL, server);

	}
}

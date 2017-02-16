/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager; 

import common.MessageInfo;

public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);
		MessageInfo msg = new MessageInfo(numMessages, 1);


		// TO-DO: Initialise Security Manager
		System.setSecurityManager(new RMISecurityManager());

		try{	// TO-DO: Bind to RMIServer
			
			iRMIServer = (RMIServerI) Naming.lookup(urlServer);
			// TO-DO: Attempt to send messages the specified number of times
			iRMIServer.receiveMessage(msg);
		}
		catch(Exception e){
			System.out.println("err: " + e.getMessage()); 
            e.printStackTrace(); 
		}	
	}
}

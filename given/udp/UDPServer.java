
/*
 * Created on 01-Mar-2016
 */
//package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

//import common.MessageInfo;

public class UDPServer {

	private DatagramSocket recvSoc;
	//private int totalMessages = -1;
	//private int[] receivedMessages;
	//private boolean close;

	private void run() {
		int			pacSize;
		byte[]		pacData;
		DatagramPacket 	pac;
		DatagramPacket mes;

		byte[] numberOfMessages = new byte[1024]; 
		try{
			mes = new DatagramPacket(numberOfMessages, numberOfMessages.length); 
			recvSoc.receive(mes); 
			String number_of_messages = new String(mes.getData(),0,mes.getLength()); 
			pacSize = Integer.parseInt(number_of_messages); //buffer size for the message
			pacData = new byte [pacSize]; //buffer for the message
			int i = -1; //counter of total messages
			int j = 0; //counter of messages received
			recvSoc.setSoTimeout(100); //set timeout
			while(i < pacSize - 1){
				pac = new DatagramPacket(pacData, pacSize); //message packet
				i++;
				try{
					recvSoc.receive(pac); //receive the actual message
					String str = new String(pac.getData(),0,pac.getLength()); 
					System.out.println("Message" + (i + 1) + ": " + str + " have been received!");
					j++;
				}catch(SocketTimeoutException e){
					System.out.println("Message" + (i + 1) + ": Timeout");
				}
			}
			
			if(j == pacSize){
				System.out.println("All messages have been received!!");
			}else{
				//i++;
				pacSize = pacSize - j;
				System.out.println("Number of messages received: " + j + "\nNumber of messages lost: " + pacSize);
			}
		}
		 catch (SocketException e1) {
		        // TODO Auto-generated catch block
		        //e1.printStackTrace();
		        System.out.println("Socket closed " + e1);

	    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
	    }
		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever

	}

	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try{
			recvSoc = new DatagramSocket(rp);
		}
		catch(SocketException e){}
		// Done Initialisation
		System.out.println("UDPServer ready");
	}

	public static void main(String args[]) {
		int	recvPort;

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer udpserver = new UDPServer(recvPort);
		udpserver.run();

		
	}

}
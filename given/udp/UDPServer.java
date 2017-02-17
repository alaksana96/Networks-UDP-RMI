/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import common.MessageInfo;

public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean close;

	private void run() {
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;
		DatagramPacket  mes;

		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
		byte[] buf = new byte[1024];

		mes = new DatagramPacket(buf, buf.length);
		try{
			recvSoc.receive(mes);
		}
		catch(IOException e){}
		String noofmessages = new String(mes.getData(), 0, mes.getLength());
		
		pacSize = Integer.parseInt(noofmessages);
		
		pacData = new byte[pacSize];

		// TO-DO: On receipt of first message, initialise the receive buffer
		try{
			recvSoc.setSoTimeout(30);

		}
		catch(SocketException l){

		}
		int TotalMessages = -1;
		int countOfRecieved = 0;


		while(TotalMessages < pacSize - 1){
			pac = new DatagramPacket(pacData, pacSize);
			TotalMessages++;
			try{
				recvSoc.receive(pac);
				String str1 = new String(pac.getData(), 0, pac.getLength());
				System.out.println("Message" + (TotalMessages+1) + ": " + str1);
				countOfRecieved++;
			}
			catch(SocketTimeoutException e){ 
			System.out.println("Message" + (TotalMessages + 1) + ": Timeout");
			}
			catch(IOException e){
				System.out.println(e.getMessage());
			}
		}

		if(countOfRecieved == pacSize){
			System.out.println("All messages recieved");
		}
		else{
			int tmp2 = pacSize-countOfRecieved;
			System.out.println("Number of messages recieved: " + countOfRecieved + ", Number of messages lost: " + tmp2);
		}
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
		UDPServer myServer = new UDPServer(recvPort);
		myServer.run();
	}

}

package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;

import voteserver.CLA;
import voteserver.VoteRequest;


public class Client implements Runnable{
	
	public enum designation{server, client};
	
	private String hostToConnect;
	private int port, myPublicKey, myPrivateKey, mySessionKey;
	private Socket mySocket = null;
	private String clientID, password, myVote;
	private ObjectInputStream oInStream = null;
	private ObjectOutputStream oOutStream = null;	
	

	public Client (String hostToConnect, int portToConnect, String clientID, String password, String myVote)
	{
		if (hostToConnect == null || Integer.toString(portToConnect) == null)
		{
			throw new IllegalArgumentException("Input values are invalid");
		}
		this.hostToConnect = hostToConnect;
		this.port = portToConnect;
		this.clientID = clientID;
		this.password = password;
		this.myVote = myVote;

	}
	
	public void run()
	{		
		try {
				
			System.out.println("[Client] Connecting to host:" + this.hostToConnect + " on port:"  + port);
			mySocket =  new Socket(this.hostToConnect, this.port);
			System.out.println("[Client] Connected established to host via " + mySocket.getLocalPort());
		
			
			//setup streams - Output stream before input stream 
			oOutStream = new ObjectOutputStream(new BufferedOutputStream(mySocket.getOutputStream()));
			oInStream = new ObjectInputStream(new BufferedInputStream(mySocket.getInputStream()));
						
			//send vote request with credentials for validation
			//initial validation number is unknown
			VoteRequest vr = new VoteRequest(this.clientID, this.password, 1, VoteRequest.voteID.valueOf(this.myVote.toUpperCase()));
			oOutStream.writeObject(vr);
			oOutStream.flush();
			
			
			closeApp();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Close input/output streams before application termination
	 */
	private void closeApp()
	{
		try {
			this.mySocket.close();
			if (oInStream != null)
				this.oInStream.close();
			if (oOutStream != null)
				this.oOutStream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) throws IOException
	{
		new Thread(CLA.getInstance()).start();
		//new Thread(new Client("localhost", 10901, "Arjun")).start();
		
		BufferedReader clientListReader = new BufferedReader(new FileReader("clientList"));
		String line = null;
		
		//Start clients
		//element = (clientID, password, vote)
		while((line = clientListReader.readLine())!=null)
		{
			StringTokenizer sTok = new StringTokenizer(line, ",");
			new Thread(new Client("localhost", 10901, sTok.nextToken(), sTok.nextToken(), sTok.nextToken())).start();
		}
	
		clientListReader.close();
	}
	

}

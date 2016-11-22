package voteserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CLA implements Runnable{

	private final int myConnectionRequestListeningPort = 10901;
	private final int maximumClientHandlerThreads = 5;
	
	private ClientHandler vRListener = null;
	private ServerSocket myServerCRLSocket;
	private ExecutorService executor = Executors.newFixedThreadPool(maximumClientHandlerThreads);
	
	private static CLA myInstance = null;
	private CLA()
	{
		//bind socket for listening
		try{
			this.myServerCRLSocket = new ServerSocket(myConnectionRequestListeningPort);
			System.out.println("Accepting incomming connections");
			
		}catch(IOException ioe)
		{
			System.err.println("Unable to bind to socket at port " + myConnectionRequestListeningPort + 
					" and get input stream. Stopping CLA application.");
			ioe.printStackTrace();
		}
	}
		
	public static CLA getInstance()
	{
		if (myInstance == null)
		{
			myInstance = new CLA();
		}
		
		return myInstance;
	}
	
	@Override
	public void run() {
		//accept connections and fork thread to handle client
		while(true)
		{
			try {
				Socket clientHandlerSocket = myServerCRLSocket.accept(); //blocking method
				executor.execute(new ClientHandler(clientHandlerSocket));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class ClientHandler implements Runnable
{
	private Socket mySocket = null;
	ObjectInputStream oInStream = null;
	ObjectOutputStream oOutStream = null;
	
	public ClientHandler(Socket myClientHandlingSocket) throws IOException 
	{
		this.mySocket = myClientHandlingSocket;
	}

	@Override
	public void run() {
		System.out.println("Started a thread for a client");
		
		//must create object output stream on both sides before we can proceed
		try {
			oOutStream = new ObjectOutputStream(this.mySocket.getOutputStream());
			oInStream = new ObjectInputStream(this.mySocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Test - Dump output
		try {
			VoteRequest recievedRequest = (VoteRequest) oInStream.readObject();
			
			//test receiver
			System.out.println("Received VoteRequest Packet");
			System.out.println(recievedRequest.toString());
		
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
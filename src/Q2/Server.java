package Q2;

import java.net.*;
import java.io.*;

public class Server {
	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = null;
		 int clientCounter=0;
		boolean isRunning=true;

		try {
			serverSocket = new ServerSocket(10118);
		}
		catch (IOException e)
		{
			System.err.println("On ne peut pas ecouter au  port: 10118.");
			System.exit(1);
		}
		System.out.println ("Le serveur est en marche, Attente de la connexion.....");


		while(isRunning){
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
			}
			catch (IOException e)
			{
				System.err.println("Accept a echoue.");
				System.exit(1);
			}

			clientCounter++;
			//Create a new thread for each connection 1 client = 1 thread
			new Thread(
					new ResponseServerThread(clientSocket,Integer.toString(clientCounter) )
			).start();

		}



	}
} 

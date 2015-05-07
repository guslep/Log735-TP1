package Q6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static int orderCounter=0;
	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = null;

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


			//Create a new thread for each connection 1 client = 1 thread
			new Thread(
					new ResponseServerThread(clientSocket )
			).start();

		}



	}

	public static    int newOrder(){
		orderCounter++;
		return orderCounter;
	}

	public static void setOrderCount(int orderCount){
		if(orderCount>orderCounter){
		orderCounter=orderCount;}
	}
} 

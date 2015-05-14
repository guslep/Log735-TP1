/******************************************************
 Cours : LOG735
 Session : Été 2015
 Groupe : 01
 Projet : Laboratoire #1
 Étudiants : [Guillaume Lépine #1]
 [Pier-Luc Menard #2]
 Code(s) perm. : [ ak35490 #1]
 [pl #2]
 Date création : [14 mai 2015]
 Date dern. modif. : [15 mai 2015]
 ******************************************************
 [Cette classe sert de classe principale pour le serveur. Lorsqu'un client veut ce connecter au serveur le serveur créé un nouveau thread pour la connection.
 cette classe est aussi responsable de garder à jour le compteur des transactions]
 ******************************************************/
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

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



package Q6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static int orderCounter=0;
	static SynchronizerServerThread syncThread;


	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = null;


		boolean isRunning=true;
        //thread de synchronisation entre les serveurs
		syncThread=new SynchronizerServerThread();
		Thread sync=new Thread(syncThread

		);
		sync.start();


        //crée un socket pour recevoir les connections des clients
		try {
			serverSocket = new ServerSocket(10118);

		}
		catch (IOException e)
		{
			System.err.println("On ne peut pas ecouter au  port: 10118.");
			System.exit(1);
		}
		System.out.println ("Le serveur est en marche, Attente de la connexion.....");

        //recoit les connections et crée un nouveau thread
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

    /**
     * permet de mettre à jour le compteur lorsqu'une nouvelle commande est passé, cette méthode envoie au thread de synchronisation le compteur de commande
     * @return Le compteur de commande
     */
	public static synchronized int newOrder(){
		orderCounter++;
		syncThread.newOrder(orderCounter);



		return orderCounter;
	}

    /**
     * Permet de spécifier la valeur du compteur de commande. Est utulisé pour mettre le compteur à jour lorsqu'il est en mode Slave
     * @param orderCount
     */
	public static synchronized void setOrderCount(int orderCount){
		if(orderCount>orderCounter){
		orderCounter=orderCount;}

	}
} 

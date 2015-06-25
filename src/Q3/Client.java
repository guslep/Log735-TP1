
/******************************************************
 Cours : LOG735
 Session : Été 2015
 Groupe : 01
 Projet : Laboratoire #1
 Étudiants : [Guillaume Lépine #1]
 [Pier-Luc Menard #2]
 Code(s) perm. : [ ak35490 #1]
 [ MENP27019200 #2]
 Date création : [14 mai 2015]
 Date dern. modif. : [15 mai 2015]
 ******************************************************
 [Représente un client, il essaie de ce connecter à tour de rôle aux serveurs spécifiés dans la classe clientConfig pour trouver les adresses des serveurs
 L'utulisateur écrit une ligne dans la console puis lorsuqe la touche enter est enfoncé le lcient envoie la ligne au serveur puis attend une réponse qui sera affiché dans la console]
 ******************************************************/


package Q3;

import sun.net.ConnectionResetException;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.Date;

public class Client {
	private static Integer currentServerId=-1;

    public static void main(String[] args) throws IOException {

        PrintWriter out = null;
        BufferedReader in = null;
        String userInput;

        Socket connectionSocket=createNewConnection();
        System.out.print ("Entree: ");
        out = new PrintWriter(connectionSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));



        while ((userInput = stdIn.readLine()) != null) {

            boolean messageSent=false;
            while (!messageSent) {
                out.println(userInput);
                try {
                    System.out.println("recu: " + in.readLine());
                    messageSent=true;
                } catch (SocketTimeoutException e) {
                    System.out.println("Temps d'attendte depasse reconnexion sur un autre serveur " );

                    connectionSocket = createNewConnection();
                    System.out.println("Retransmission de la commande: " + userInput);

                    out = new PrintWriter(connectionSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));


                } catch (SocketException e) {

                    connectionSocket = createNewConnection();
                    System.out.println("Connexion ferme, reconnexion sur un autre serveur ");

                    System.out.println("Retransmission de la commande: " + userInput);

                    out = new PrintWriter(connectionSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                }
            }

            System.out.print("Entree: ");
        }

        out.close();
        in.close();
        stdIn.close();
        connectionSocket.close();







    }
    /**
     * Génère un nouveau socket puis ce connecte à un serveur sur le port 11018. Si le premier serveur sépcifié dans la classe
     * clientCOnfig ne répond pas la méthode essaie le prochain jusqu'à ce qu'ils aient tous été essseyé ou qu'une connexion soit établie
     * @return socket connecté à un serveur
     */
    private static Socket createNewConnection(){
        String location=clientConfig.getNewServer(currentServerId);


        String serverHostname =location.split(":")[0] ;
        Integer portNumber=Integer.parseInt(location.split(":")[1]);
       //a enlever
        System.out.println ("Essai de se connecter a l'hote " +
                serverHostname + " au port "+location.split(":")[1]);
        currentServerId++;
        if(currentServerId>clientConfig.getMaxServerId()){
            currentServerId=0;
        }
        Socket echoSocket = null;


        try {
            echoSocket = new Socket(serverHostname, 10118);
            echoSocket.setSoTimeout(10000);

            System.out.println ("Connecte a " +
                    serverHostname + " au port "+location.split(":")[1]);

        } catch (UnknownHostException e) {

            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error: The application is not currently availaible. Please contact the support line");
            System.exit(1);
        }


        return echoSocket;
    }

}


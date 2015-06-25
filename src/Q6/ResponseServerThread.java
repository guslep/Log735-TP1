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
 [Thread dédié à une connexion, recoit les données du client, incérmente le conmpteur du  serveur puis renvoies la réponse

 ]
 ******************************************************/




package Q6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Gus on 5/7/2015.
 */
public class ResponseServerThread implements Runnable{

    private Socket clientSocket;
    private String  clientId;

        // the thread will wait for client input and send it back in uppercase
    @Override
    public void run() {
        System.out.println ("connexion reussie");
        System.out.println ("Attente de l'entree.....");

        PrintWriter out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                System.out.println ("Serveur: " + inputLine);
                if(inputLine.split("---")[0].equals("update")){
                    Server.setOrderCount(Integer.parseInt(inputLine.split("---")[1]));
                }
                if (inputLine.equals("Bye"))
                    break;
                inputLine = inputLine.toUpperCase();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println("#"+ Server.newOrder()+" - " +inputLine);

            }
            //close everything when the client disconnect
            out.close();
            in.close();
            clientSocket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //receive the socket created and the client id

    /**
     * Constructeur
     * @param socket : socket de la connection avec le client
     */
    public ResponseServerThread(Socket socket){

        clientSocket=socket;

    }
}

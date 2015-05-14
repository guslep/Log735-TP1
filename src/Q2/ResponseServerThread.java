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
 [Thread dédié à une connexion, recoit les données du client, incérmente le conmpteur du  serveur puis renvoies la réponse

 ]
 ******************************************************/

package Q2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


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
                if (inputLine.equals("Bye"))
                    break;
                inputLine = inputLine.toUpperCase();
                out.println("To "+ clientId+": " +inputLine);

            }

            out.close();
            in.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    /**
     * Constructeur
     * @param socket : socket de la connection avec le client
     */
    public ResponseServerThread(Socket socket,String clientId){
        this.clientId=clientId;
        clientSocket=socket;

    }
}

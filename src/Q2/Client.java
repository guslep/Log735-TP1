
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

package Q2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws IOException {

		String serverHostname = new String ("127.0.0.1");

        if (args.length > 0)
        	serverHostname = args[0];
        System.out.println ("Essai de se connecter a l'hote " +
		serverHostname + " au port 10118.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, 10118);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Hote inconnu: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Ne pas se connecter au serveur: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.print ("Entree: ");
        while ((userInput = stdIn.readLine()) != null) {
        	out.println(userInput);
        	System.out.println("recu: " + in.readLine());
            System.out.print ("Entree: ");
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}


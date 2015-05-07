package Q5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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

    private static Socket createNewConnection(){
        String location= clientConfig.getNewServer(currentServerId);


        String serverHostname =location.split(":")[0] ;
        Integer portNumber=Integer.parseInt(location.split(":")[1]);
       //a enlever
        System.out.println ("Essai de se connecter a l'hote " +
                serverHostname + " au port "+location.split(":")[1]);
        currentServerId++;
        if(currentServerId> clientConfig.getMaxServerId()){
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


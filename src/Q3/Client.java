package Q3;

import java.io.*;
import java.net.Socket;
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
        System.out.print ("Entree: ");
        Socket connectionSocket=createNewConnection();
        out = new PrintWriter(connectionSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));



        while ((userInput = stdIn.readLine()) != null) {
            Date sent=new Date();
            out.println(userInput);
            try{System.out.println("recu: " + in.readLine());}
            catch (SocketTimeoutException e){
              connectionSocket=createNewConnection();
                out = new PrintWriter(connectionSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            }

            System.out.print ("Entree: ");
        }

        out.close();
        in.close();
        stdIn.close();
        connectionSocket.close();







    }

    private static Socket createNewConnection(){
        String location=clientConfig.getNewServer(currentServerId);

        String serverHostname =location.split(":")[0] ;
        Integer portNumber=Integer.parseInt(location.split(":")[1]);
        System.out.println ("Essai de se connecter a l'hote " +
                serverHostname + " au port"+location.split(":")[1]);
        currentServerId++;
        if(currentServerId>=clientConfig.getMaxServerId()){
            currentServerId=0;
        }
        Socket echoSocket = null;


        try {
            echoSocket = new Socket(serverHostname, 10118);
            echoSocket.setSoTimeout(10000);

        } catch (UnknownHostException e) {
            System.err.println("Hote inconnu: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Ne pas se connecter au serveur: " + serverHostname);
            System.exit(1);
        }



        return echoSocket;
    }

}


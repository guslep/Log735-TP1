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

            out.close();
            in.close();
            clientSocket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //receive the socket created and the client id
    public ResponseServerThread(Socket socket){

        clientSocket=socket;

    }
}

package Q6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by Gus on 5/7/2015.
 */
public class SynchronizerServerThread implements Runnable{


    private Socket clientSocket;
    private static boolean backupConnectionDown =true;
    private String  clientId;
    private PrintWriter out = null;
    private boolean slave=true;

        // the thread will wait for client input and send it back in uppercase

    ServerSocket serverSocket=null;
    @Override
    public void run() {
        System.out.println("Sync tread partie");


        try {
           serverSocket = new ServerSocket(10119);
            clientSocket = serverSocket.accept();
            System.out.println("listening on 10119");


        } catch (IOException e) {
            System.err.println("On ne peut pas ecouter au  port: 10119.");
            System.exit(1);
        }


        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                int counter = Integer.parseInt(inputLine.split(":")[1]);
                Server.setOrderCount(counter);


            }


            in.close();
            clientSocket.close();


        } catch (SocketException e) {
           setMaster();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
        //receive the socket created and the client id
    public SynchronizerServerThread(){



        System.out.println ("Le serveur est en mode slave....");


    }

    public void newOrder(int counterNumber){
        if(backupConnectionDown&&!slave){
            createSocket();
        }
        if(slave){
        setMaster();
        System.out.println("server est master");
    }


        if(!backupConnectionDown){
            out.println("Counter:"+counterNumber);

        }


    }
    private void setMaster(){
        slave=false;
        createSocket();

}
private void createSocket(){

    Socket connectionSocket=createNewConnection();
    if(connectionSocket!=null){

    try {
        out = new PrintWriter(connectionSocket.getOutputStream(), true);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }else{
        System.out.println("Could not connect to backup");
    }
}


    private static Socket createNewConnection(){
        String location= serverConfig.getNewServer(0);


        String serverHostname =location.split(":")[0] ;
        Integer portNumber=Integer.parseInt(location.split(":")[1]);
        //a enlever
        System.out.println ("Essai de se connecter a l'hote " +
                serverHostname + " au port "+location.split(":")[1]);

        Socket echoSocket = null;


        try {
            echoSocket = new Socket();
            echoSocket.connect(new InetSocketAddress(serverHostname, 10119),1000);
            //echoSocket.setSoTimeout(1000);
            backupConnectionDown =false;
            System.out.println ("Connecte a " +
                    serverHostname + " au port "+location.split(":")[1]);



        } catch (UnknownHostException e) {

            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error: The application is not currently availaible. Please contact the support line");
                backupConnectionDown =true;
            echoSocket=null;






        }


        return echoSocket;
    }



}

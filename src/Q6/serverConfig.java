package Q6;

/**
 * Created by Gus on 5/7/2015.
 */
abstract public class serverConfig {
private static final String[] SERVERLOCATION ={"10.196.113.59:10119"};
    public static String getNewServer(int currentServer){
            String newServer="";
        if(currentServer>=SERVERLOCATION.length-1){
            newServer=SERVERLOCATION[0];
        }else{
            newServer=SERVERLOCATION[currentServer+1];
        }
        return newServer;
    }


    public static int getMaxServerId(){
        return SERVERLOCATION.length-1;
    }

}

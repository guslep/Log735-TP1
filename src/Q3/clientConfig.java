package Q3;

/**
 * Created by Gus on 5/7/2015.
 */
abstract public class  clientConfig {
private static final String[] SERVERLOCATION ={"127.0.0.1:10118","10.196.113.59:10118"};
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

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
 [Configuration du serveur
 ]
 ******************************************************/
package Q6;

/**
 * Created by Gus on 5/7/2015.
 */
abstract public class serverConfig {
    //adresse ip du serveur de backup
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

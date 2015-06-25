package Q3;
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
 [Configuration du client
 ]
 ******************************************************/
abstract public class  clientConfig {
private static final String[] SERVERLOCATION ={"127.0.0.1:10118","10.196.113.59:10118"};

    /**
     *Lorsqu'un client perd la connection cette méthode lui permet d'obtenir le prochain serveur disponible
     * @param currentServer le numéro du serveur courant
     * @return l'adresse du serveur à contacter
     */
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

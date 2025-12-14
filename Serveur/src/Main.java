import serveur.ServeurUdp;

public class Main {
    public static void main(String[] args) {
        ServeurUdp serveur = new ServeurUdp();
        
        // Ajouter un hook pour arrêter proprement le serveur
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serveur.stop();
        }));

        serveur.start();
    }
}

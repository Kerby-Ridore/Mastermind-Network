import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Serveur {

    public static void main(String args[]) throws IOException {

        int port = 1234;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Serveur en attente de connexion sur le port " + port);
            while (true) {
                Socket server = serverSocket.accept();
                System.out.println("Connexion établie");

                //générer une combinaison secrète aléatoire
                Code secretCode = new Code(new Random());
                System.out.println(secretCode);


                //Affiche la chaine reçue du client
                DataInputStream in = new DataInputStream(server.getInputStream());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());

                while(true) {

                    String CombinationClient = in.readUTF().toUpperCase();
                    System.out.println("Combinaison proposé par le client: " + CombinationClient);

                    Code guess =new Code(CombinationClient);

                    //Affiche le nombre de couleurs correctement positionnées
                    int correctPositions = secretCode.numberOfColorsWithCorrectPosition(guess);
                    System.out.println("Couleurs correctement positionnées : " + correctPositions);

                    //affiche le nombre de couleurs incorrectement positionnées
                    int incorrectPositions = secretCode.numberOfColorsWithIncorrectPosition(guess);
                    System.out.println("Couleurs incorrectement positionnées : " + incorrectPositions);


                    //Envoie des résultats au client
                    out.writeUTF(correctPositions + "," + incorrectPositions);

                    //Vérification de la combinaison du client
                    if (correctPositions == 4){
                        System.out.println("Le client a réussi");
                        break;

                    }


                }




                server.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();

        }
    }
}

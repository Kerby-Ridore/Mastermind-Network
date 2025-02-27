import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
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

                // Générer une combinaison secrète aléatoire
                Code secretCode = new Code(new Random());
                System.out.println("Code secret généré: " + secretCode);

                // Affiche la chaîne reçue du client
                DataInputStream in = new DataInputStream(server.getInputStream());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());

                boolean found = false;
                while (!found) {
                    try {
                        String combinationClient = in.readUTF().toUpperCase();
                        System.out.println("Combinaison proposée par le client: " + combinationClient);

                        Code guess = new Code(combinationClient);

                        // Affiche le nombre de couleurs correctement positionnées
                        int correctPositions = secretCode.numberOfColorsWithCorrectPosition(guess);
                        System.out.println("Couleurs correctement positionnées : " + correctPositions);

                        // Affiche le nombre de couleurs incorrectement positionnées
                        int incorrectPositions = secretCode.numberOfColorsWithIncorrectPosition(guess);
                        System.out.println("Couleurs incorrectement positionnées : " + incorrectPositions);

                        // Envoie des résultats au client
                        out.writeUTF("Position correcte: " + correctPositions + ", position incorrecte: " + incorrectPositions);

                        // Vérification de la combinaison du client
                        if (correctPositions == 4) {
                            found = true;
                            System.out.println("Le client a réussi");
                        }
                    } catch (EOFException e) {
                        System.out.println("Le client a fermé la connexion");
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }

                // Fermer les flux et la connexion
                in.close();
                out.close();
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {

        String serverName = "localhost";
        int port = 1234;

        try{
            Socket client = new Socket(serverName, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());

            //ajout de la boucle

            while(true){

                try {

                    //Lecture de la combinaison à tester
                    System.out.print("Entrez une combinaison de 4 couleurs (B, G, O, R, W, Y) : ");
                    String message = reader.readLine().toUpperCase();

                    //vérification de la validité de l'entrée
                    if (!isValidCombination(message)) {
                        System.out.println("Combinaison invalide, Veuillez entrer une combinaison de  4 couleurs parmi B, G, O, R, W, Y.");
                        continue;
                    }
                    out.writeUTF(message); // Envoi de la combinaison à tester au serveur

                    //reception du nombre de couleurs correctement et incorrectement positionnées
                    String response = in.readUTF().toUpperCase();
                    System.out.println("Réponse du serveur: " + response);

                    //Vérification de la combinaison test
                    if (response.equals("POSITION CORRECTE: 4, POSITION INCORRECTE: 0")) {
                        System.out.println("Félicitations");
                        break;
                    }
                }
                catch(EOFException e){
                    System.out.println("Le serveur a fermé la connexion");
                    break;
                }
            }
            in.close();
            out.close();
            client.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    //Méthode pour vérifier la validité de l'entrée
    private static boolean isValidCombination(String message) {
        if (message.length() != 4) {
            return false;
        }
        for (char c : message.toCharArray()) {
            if ("BGORWY".indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

}

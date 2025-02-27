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

                //Lecture de la combinaison à tester
                System.out.print("Entrée votre combinaison à tester: ");
                String message = reader.readLine().toUpperCase();
                out.writeUTF(message); // Envoi de la combinaison à tester au serveur

                //reception du nombre de couleurs correctement et incorrectement positionnées
                String response = in.readUTF().toUpperCase();
                System.out.println("Réponse du serveur: " + response);

                //Vérification de la combinaison test
                if (response.equals("4,0")) {
                    System.out.println("Félicitations");
                    break;
                }
            }
            client.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

}

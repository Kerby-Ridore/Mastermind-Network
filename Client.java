import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {

        String serverName = "localhost";
        int port = 1234;

        try{
            Socket client = new Socket(serverName, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Entrée votre combinaison: ");
            String message = reader.readLine();

            OutputStream outToserver = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToserver);
            out.writeUTF(message);

            client.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

}

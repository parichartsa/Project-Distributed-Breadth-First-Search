import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TspClient {
    public static void main(String[] args) {
        try {
            // connect to Server at IP Address and Port that require
            Socket socket = new Socket("192.168.1.62", 5000);


            // create Reader and Writer for read data and connect to Server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // sent data input to server
            String input = "TSP input";
            out.println(input);
            System.out.println("Sent: " + input);

            // wait result from server
            String response = in.readLine();
            System.out.println("Received: " + response);

            // close the connection
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TspClient {
    public static void main(String[] args) {
        try {
            String serverIP = "192.168.1.180";
            int serverPort = 5500;

            Socket socket = new Socket(serverIP, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String cities = "City A,City B,City C,City D"; // ตัวอย่างเมืองที่ส่งไปคำนวณ
            out.println(cities);

            String response = in.readLine();
            System.out.println("Received: " + response);

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

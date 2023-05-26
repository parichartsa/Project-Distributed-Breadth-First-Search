import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TspClient {
    public static void main(String[] args) {
        try {
            // ทำการเชื่อมต่อกับ Server ที่ IP Address และ Port ที่กำหนด
            Socket socket = new Socket("server-ip-address", 5000);

            // สร้าง Reader และ Writer เพื่ออ่านข้อมูลเข้าและส่งข้อมูลไปยัง Server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // ส่งข้อมูล input ไปยัง Server
            String input = "TSP input";
            out.println(input);
            System.out.println("Sent: " + input);

            // รอรับผลลัพธ์จาก Server
            String response = in.readLine();
            System.out.println("Received: " + response);

            // ปิดการเชื่อมต่อ
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

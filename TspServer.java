import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TspServer {
    public static void main(String[] args) {
        try {
            // สร้าง ServerSocket ให้เฝ้ารอการเชื่อมต่อจาก Client ที่ Port 5000
            ServerSocket serverSocket = new ServerSocket(5000);
            
            System.out.println("Server started. Waiting for clients...");

            // รอการเชื่อมต่อจาก Client
            Socket clientSocket = serverSocket.accept();

            System.out.println("Client connected.");

            // สร้าง Reader และ Writer เพื่ออ่านข้อมูลเข้าและส่งข้อมูลไปยัง Client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);

                // ทำการแก้ปัญหา TSP และส่งผลลัพธ์กลับไปยัง Client
                String result = solveTSP(inputLine);
                out.println(result);
            }

            // ปิดการเชื่อมต่อ
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ฟังก์ชันแก้ปัญหา TSP
    private static String solveTSP(String input) {
        // โค้ดการแก้ปัญหา TSP ขนาดไม่เกิน 4 เมือง
        // ...

        // ส่งผลลัพธ์กลับในรูปแบบ String
        return "TSP solution";
    }
}

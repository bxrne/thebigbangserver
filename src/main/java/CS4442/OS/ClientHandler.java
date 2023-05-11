package CS4442.OS;
import java.io.*;
import java.net.Socket;



public class ClientHandler extends Thread {
    private Socket clientSocket;
    private Commands commands = new Commands();

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            boolean running = true;
            while (running) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String message = reader.readLine().toString();
                System.out.println("Received message from " + clientSocket.getInetAddress() + ": " + message);

                

                String response = commands.parse(message);
    
                PrintWriter writer = new PrintWriter(outputStream);
                writer.println(response);
                writer.flush();
                
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

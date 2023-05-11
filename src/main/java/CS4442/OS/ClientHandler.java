package CS4442.OS;
import java.io.*;
import java.net.Socket;



public class ClientHandler extends Thread {
    private Socket clientSocket;
    private Commands commands = new Commands();
    private boolean running = true;


    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
            
            while (running) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                if (reader.ready() == false) {
                    continue;
                }

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

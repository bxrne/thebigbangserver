package CS4442.OS;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;

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
                String message = reader.readLine();
                System.out.println("Received message from " + clientSocket.getInetAddress() + ": " + message);

                if (message.equals("exit")) {
                    running = false;
                } else {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                    String reversedMessage = new StringBuilder(message).reverse().toString();
                    writer.write(reversedMessage + "\n");
                    writer.flush();
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

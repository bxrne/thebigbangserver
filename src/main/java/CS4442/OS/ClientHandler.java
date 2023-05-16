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
            PipedInputStream pipedInputStream = new PipedInputStream();
            PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(pipedInputStream));

            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            while (running) {
                if (reader.ready()) {
                    String message = reader.readLine().toString();
                    System.out.println(
                            "Client@" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " sent: " + message);

                    String response = commands.parse(message);

                    PrintWriter writer = new PrintWriter(outputStream);
                    writer.println(response);
                    writer.flush();
                }

                if (inputStream.available() > 0) {
                    byte[] buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);
                    pipedOutputStream.write(buffer);
                    pipedOutputStream.flush();
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

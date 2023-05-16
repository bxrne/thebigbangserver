package CS4442.OS;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private Commands commands = new Commands();
    private boolean running = true;
    private PipedInputStream inPipe = new PipedInputStream();
    private PipedOutputStream outPipe = new PipedOutputStream();

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Set up the pipes
            PipedOutputStream toClient = new PipedOutputStream(inPipe);
            PipedInputStream fromClient = new PipedInputStream(outPipe);

            // Start separate threads to read from and write to the pipes
            Thread readerThread = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fromClient));
                    while (running) {
                        if (reader.ready()) {
                            String message = reader.readLine().toString();
                            System.out.println(
                                    "Client@" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " sent: " + message);
                            String response = commands.parse(message);
                            PrintWriter writer = new PrintWriter(toClient);
                            writer.println(response);
                            writer.flush();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread writerThread = new Thread(() -> {
                try {
                    OutputStream outputStream = clientSocket.getOutputStream();
                    while (running) {
                        if (inPipe.available() > 0) {
                            byte[] buffer = new byte[inPipe.available()];
                            inPipe.read(buffer);
                            String message = new String(buffer);
                            PrintWriter writer = new PrintWriter(outputStream);
                            writer.println(message);
                            writer.flush();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            readerThread.start();
            writerThread.start();

            while (running) {
                if (clientSocket.isClosed()) {
                    running = false;
                }
            }

            clientSocket.close();
            toClient.close();
            fromClient.close();
            inPipe.close();
            outPipe.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

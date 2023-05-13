package CS4442.OS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private ServerSocket serverSocket;
    private boolean running = true;
    private ExecutorService pool;

    public static void main(String[] args) {
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            serverThread.interrupt();
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Server started");
        try {
            serverSocket = new ServerSocket(1234);
            pool = Executors.newCachedThreadPool();
            while (running) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                pool.execute(clientHandler);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void shutdown() {
        try {
            running = false;
            broadcast("Server shutting down");
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }

            for (ClientHandler client : clients) {
                client.shutdown();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.send(message);
        }
    }

    public class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("The Big Bang Server\nEnter a nickname: ");

                String nickname = in.readLine(); // TODO: validate nickname
                System.out.println("New client connected: " + nickname);
                broadcast(nickname + " has joined the chat");

                // listen for messages from client
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equals("/quit")) {
                        System.out.println(nickname + " has left the chat");
                        broadcast(nickname + " has left the chat");
                        shutdown();
                        break;
                    }

                    System.out.println(nickname + ": " + message);
                    broadcast(nickname + ": " + message);
                }

            } catch (Exception e) {
                shutdown();
                e.printStackTrace();
            }
        }

        public void send(String message) {
            out.println(message);
        }

        public void shutdown() {
            try {
                in.close();
                out.close();

                if (!socket.isClosed()) {
                    socket.close(); // close any open clients on server shutdown
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

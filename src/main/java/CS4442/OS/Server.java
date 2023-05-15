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
import java.util.logging.Logger;

import CS4442.OS.Command.ServerSignals;

public class Server implements Runnable {
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private Logger logger = Logger.getLogger(Server.class.getName());
    private ServerSocket serverSocket;
    private boolean running = true;

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
        try {
            serverSocket = new ServerSocket(1234);
            logger.info("Server started");

            ExecutorService pool = Executors.newCachedThreadPool();
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
        Message shutdownMsg = new Message("Server", "shutting down");
        logger.info("Server shutting down");

        try {
            running = false;
            broadcast(shutdownMsg);
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }

            for (ClientHandler client : clients) {
                client.shutdown();
            }

        } catch (IOException e) {
            logger.warning("Server shutdown failed");
            e.printStackTrace();
        }
    }

    public void broadcast(Message message) {
        for (ClientHandler client : clients) {
            client.send(message);
        }
    }

    public class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println("The Big Bang Server\nEnter a nickname:");

                nickname = in.readLine();
                if (nickname == null || nickname.equals("")) {
                    Message invalidMsg = new Message("Server", "Invalid nickname");
                    out.println(invalidMsg);
                    shutdown();
                    return;
                }

                broadcast(new Message("Server", "Welcome to the chat, " + nickname));
                out.println(new Message("Server", "Type /help for a list of commands"));
                System.out.println(new Message("Server", nickname + " has joined the chat"));

                String message;
                while ((message = in.readLine()) != null) {
                    Message msg = new Message(nickname, message);

                    if (msg.validate()) {
                        if (msg.getBody().charAt(0) == '/') {
                            handleCommand(msg, nickname);
                        } else {
                            broadcast(msg);
                        }

                        System.out.println(msg);

                    } else {
                        out.println(new Message("Server", "Invalid message"));
                    }
                }

                shutdown();

            } catch (Exception e) {
                Message goodbyeMsg = new Message("Server", nickname + " has left the chat");
                out.println(goodbyeMsg);
                System.out.println(goodbyeMsg);
            }
        }

        private void handleCommand(Message msg, String nickname) {
            try {
                Command command = new Command(msg.getBody().substring(1));
                ServerSignals signal = command.execute(out);

                if (signal == ServerSignals.QUIT) {
                    Message quitMsg = new Message("Server", nickname + " has left the chat");
                    out.println(quitMsg);
                    shutdown();
                    return;
                }

                if (signal == ServerSignals.LIST) {
                    Message listMsg = new Message("Server", clients.size() + " online");
                    out.println(listMsg);
                }

            } catch (IllegalArgumentException e) {
                Message invalidMsg = new Message("Server", "Invalid command");
                out.println(invalidMsg);
            }
        }

        public void send(Message message) {
            out.println(message);
        }

        public void shutdown() {
            try {
                in.close();
                out.close();

                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                logger.warning("Client shutdown failed");
                e.printStackTrace();
            }
        }

    }

}

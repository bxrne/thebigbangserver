package CS4442.OS;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        try {
            FileHandler fileHandler = new FileHandler("server.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }

        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            logger.info("Server@localhost:1234 started");

            while (serverSocket.isBound()) {
                try (Socket clientSocket = serverSocket.accept()) {
                    logger.info(
                            "Client@" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " connected");

                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    clientHandler.start();
                } catch (IOException e) {
                    logger.severe(e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }

        logger.info("Server@localhost:1234 stopped");

    }
}

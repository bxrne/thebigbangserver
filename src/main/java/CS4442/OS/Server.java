package CS4442.OS;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started on port 1234");

            while (serverSocket.isBound()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());

                // Create a new thread to handle the client connection
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Server stopped");
        }
    }
}

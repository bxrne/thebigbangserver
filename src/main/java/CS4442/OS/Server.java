package CS4442.OS;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server@localhost:1234 started");

            while (serverSocket.isBound()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client@" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Server@localhost:1234 stopped");
        }
    }
}

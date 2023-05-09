package CS4442.OS;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		try {
			// Create a server socket on port 1234
			ServerSocket serverSocket = new ServerSocket(1234);
			System.out.println("Server started on port 1234");

			// Wait for a client to connect
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected from " + clientSocket.getInetAddress());

			// Create input and output streams for the client socket
			InputStream inputStream = clientSocket.getInputStream();
			OutputStream outputStream = clientSocket.getOutputStream();

			// Read the message from the client
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String message = reader.readLine();
			System.out.println("Received message: " + message);

			// Send a response to the client
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
			// write reversed message back to client
			String reversedMessage = new StringBuilder(message).reverse().toString();
			writer.write(reversedMessage + "\n");

			writer.flush();

			// Close the sockets
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


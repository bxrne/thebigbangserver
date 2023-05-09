package CS4442.OS;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(1234);
			System.out.println("Server started on port 1234");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected from " + clientSocket.getInetAddress());

			boolean running = true;
			while (running) {
				InputStream inputStream = clientSocket.getInputStream();
				OutputStream outputStream = clientSocket.getOutputStream();

				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String message = reader.readLine();
				System.out.println("Received message: " + message);

				if (message.equals("exit")) {
					running = false;
				}else {
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
					String reversedMessage = new StringBuilder(message).reverse().toString();
					writer.write(reversedMessage + "\n");
					writer.flush();
				}
			}

			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


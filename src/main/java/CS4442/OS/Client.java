package CS4442.OS;

import java.io.*;
import java.net.*;


public class Client {
	public static void main(String[] args) {
		try {
			// Create a client socket and connect to the server on port 1234
			Socket socket = new Socket("localhost", 1234);
			System.out.println("Connected to server on port 1234");

			// Create input and output streams for the socket
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

//			// Send a message to the server
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
//			writer.write("Hello from client\n");
//			writer.flush();
//
//			// Read the response from the server
//			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//			String response = reader.readLine();
//			System.out.println("Received response: " + response);
			System.out.println("Anything you type will be sent to the server and returned reversed.");
			System.out.println("Type 'exit' to quit.\n");
			boolean running = true;
			while (running) {
				// send text to server form input
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String message = reader.readLine();

				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
				writer.write(message + "\n");
				writer.flush();

				if (message.equals("exit")) {
					running = false;
				}

				// read response from server
				reader = new BufferedReader(new InputStreamReader(inputStream));
				String response = reader.readLine();
				System.out.println("Received response: " + response);


			}

			// Close the socket
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package CS4442.OS;

import java.io.*;
import java.net.*;


public class Client {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 1234);
			System.out.println("Connected to server on port 1234");

			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			System.out.println("Anything you type will be sent to the server and returned reversed.");
			System.out.println("Type 'exit' to quit.\n");

			boolean running = true;
			while (running) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String message = reader.readLine();

				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
				writer.write(message + "\n");
				writer.flush();

				if (message.equals("exit")) {
					running = false;
				}else{
					reader = new BufferedReader(new InputStreamReader(inputStream));
					String response = reader.readLine();
					System.out.println("Received response: " + response);
				}

			}

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

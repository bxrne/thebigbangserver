package CS4442.OS;

import java.io.*;
import java.net.*;


public class Client {
	public static void main(String[] args) {
		
		try {
			System.out.println("\nWelcome to the Big Bang Server");
			System.out.println("Type /help for a list of commands\n");

			Socket socket = new Socket("localhost", 1234);
			System.out.println("Connected to localhost:1234");

			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			boolean running = true;
			while (running) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String message = reader.readLine().toString();
				PrintWriter writer = new PrintWriter(outputStream);
				writer.println(message);
				writer.flush();

				reader = new BufferedReader(new InputStreamReader(inputStream));
				message = (reader.readLine().toString());

				if (message.equals("exit")) {
					System.out.println("Exiting...");
					running = false;
					socket.close();
				}else {
					System.out.println(message);
				}
				
			}

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
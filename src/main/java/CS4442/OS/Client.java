package CS4442.OS;

import java.io.*;
import java.net.*;

public class Client implements Runnable {
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	private boolean running = true;

	@Override
	public void run() {
		try {
			client = new Socket("localhost", 1234);

			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			InputHandler inputHandler = new InputHandler();
			Thread inputThread = new Thread(inputHandler);
			inputThread.start();

			String serverResponse;
			while (running) {
				serverResponse = in.readLine();

				if (serverResponse != null) {
					System.out.println(serverResponse);
				}
			}

		} catch (IOException e) {
			shutdown();
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			running = false;
			in.close();
			out.close();
			if (!client.isClosed()) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class InputHandler implements Runnable {
		@Override
		public void run() {
			try {
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

				while (running) {
					String userInput = stdIn.readLine();
					if (userInput.equals("/quit")) {
						stdIn.close();
						shutdown();
					} else {
						out.println(userInput); // send to server
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		Client client = new Client();
		client.run();
	}

}
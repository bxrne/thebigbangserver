package CS4442.OS;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class Client implements Runnable {
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	private boolean running = true;
	private InputHandler inputHandler;
	private Thread inputThread;
	private Logger logger = Logger.getLogger(Client.class.getName());

	@Override
	public void run() {
		try {
			client = new Socket("localhost", 1234);

			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			inputHandler = new InputHandler();
			inputThread = new Thread(inputHandler);
			inputThread.start();

			String serverResponse;
			while (running) {
				serverResponse = in.readLine();

				if (serverResponse != null) {
					System.out.println(serverResponse);
				} else {
					shutdown();
				}
			}
			inputThread.join();
			in.close();
			out.close();
			client.close();

		} catch (IOException | InterruptedException e) {
			logger.warning("Client shutting down");
			inputThread.interrupt();
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
			logger.warning("Client shutting down");
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
						out.println(userInput);
						shutdown();
					} else {
						out.println(userInput); // send to server
					}
				}

			} catch (IOException e) {
				logger.warning("Client shutting down");
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		Client client = new Client();
		client.run();

		try {
			client.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
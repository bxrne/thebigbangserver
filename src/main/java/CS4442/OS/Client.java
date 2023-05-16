package CS4442.OS;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class Client implements Runnable {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private boolean running = true;
	private Thread inputThread;
	private Logger logger = Logger.getLogger(Client.class.getName());

	@Override
	public void run() {
		try {
			clientSocket = new Socket("localhost", 1234);

			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			InputHandler inputHandler = new InputHandler();
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
			clientSocket.close();

		} catch (IOException | InterruptedException e) {
			logger.warning("Client shutting down");
			inputThread.interrupt();
			shutdown();
			// e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			running = false;
			in.close();
			out.close();
			if (!clientSocket.isClosed()) {
				clientSocket.close();
			}
		} catch (IOException e) {
			logger.warning("Client shutting down");
			// e.printStackTrace();
		}
	}

	class InputHandler implements Runnable {
		@Override
		public void run() {
			try {
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

				while (running) {
					ClientMessage clientMessage = new ClientMessage(stdIn.readLine());
					if (clientMessage.validate()) {
						String userInput = clientMessage.getBody();

						if (userInput.equals("/quit")) {
							stdIn.close();
							out.println(userInput);
							shutdown();
						} else {
							out.println(userInput); // send to server
						}
					}
				}

			} catch (IOException e) {
				logger.warning("Client shutting down");
				// e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		Client client = new Client();
		client.run();

		try {
			client.clientSocket.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}

	}
}
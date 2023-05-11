package CS4442.OS;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    // Run tests on the Server.java class
    public static void main(String[] args) {
        Server.main(args);
    }

    @Test
    public void testServer() {
        try (ServerSocket socketServer = new ServerSocket()) {
            Server.listen(socketServer);
            try (Socket clientSocket = new Socket("localhost", socketServer.getLocalPort())) {
                assertTrue(clientSocket.isConnected());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

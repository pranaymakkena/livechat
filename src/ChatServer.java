import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static Set<String> clientNames = new HashSet<>();

    public static void main(String[] args) throws Exception {
        System.out.println("Chat server started...");
        int port = 12345;  // Define port
        ServerSocket serverSocket = new ServerSocket(port);
        
        while (true) {
            new ClientHandler(serverSocket.accept()).start();
        }
    }

    private static class ClientHandler extends Thread {
        private String clientName;
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                out.println("Enter your name: ");
                clientName = in.readLine();
                synchronized (clientNames) {
                    if (clientNames.contains(clientName)) {
                        out.println("Name already taken. Please choose another one.");
                        return;
                    }
                    clientNames.add(clientName);
                }

                broadcast(clientName + " has joined the chat!");

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("quit")) {
                        break;
                    }
                    broadcast(clientName + ": " + message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
                synchronized (clientNames) {
                    clientNames.remove(clientName);
                }

                broadcast(clientName + " has left the chat.");
            }
        }

        private void broadcast(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}

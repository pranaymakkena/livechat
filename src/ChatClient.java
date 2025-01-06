import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private JTextArea chatArea;
    private JTextField inputField;

    public ChatClient() {
        // Create the GUI
        JFrame frame = new JFrame("Chat Client");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false); // Make chat area read-only
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        // Add components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.add(sendButton, BorderLayout.EAST);

        // Add action listener to the send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Add action listener to the input field for Enter key
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        frame.setVisible(true);

        // Connect to the server
        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345); // Connect to the server
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Start a thread to listen for incoming messages
            new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        chatArea.append(message + "\n");
                    }
                } catch (IOException e) {
                    chatArea.append("Connection closed.\n");
                }
            }).start();
        } catch (IOException e) {
            chatArea.append("Unable to connect to the server.\n");
        }
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            writer.println(message); // Send the message to the server
            inputField.setText(""); // Clear the input field
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient::new);
    }
}
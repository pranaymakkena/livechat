import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ChatClient {
    private String serverAddress = "localhost";  // Set to Heroku server if deploying online
    private int serverPort = 12345;

    private JTextArea textArea;
    private JTextField textField;
    private PrintWriter out;
    private Socket socket;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClient().createAndShowGUI());
    }

    public void createAndShowGUI() {
        // Set up the main frame
        JFrame frame = new JFrame("Chat Client");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the chat history area
        textArea = new JTextArea(15, 50);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(new Color(240, 240, 240));
        textArea.setForeground(new Color(50, 50, 50));
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Set up the message input field
        textField = new JTextField(50);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(new Color(255, 255, 255));
        textField.setForeground(new Color(0, 0, 0));
        textField.addActionListener(e -> sendMessage());
        frame.add(textField, BorderLayout.SOUTH);

        // Create a panel for additional layout (e.g., a button to send messages if needed)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(e -> sendMessage());
        bottomPanel.add(sendButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Set up frame size and visibility
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);

        // Connect to the server
        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                if (serverMessage.equals("Enter your name: ")) {
                    String name = JOptionPane.showInputDialog("Enter your name:");
                    out.println(name);
                } else {
                    textArea.append(serverMessage + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = textField.getText();
        if (!message.isEmpty()) {
            out.println(message);
            textField.setText("");  // Clear the text field after sending
        }
    }
}

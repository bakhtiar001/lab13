package exercise3;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	//run server frame
                new ServerFrame();
            }
        });
    }
}

//server frame
class ServerFrame extends JFrame {

    private JTextArea textArea;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    public ServerFrame() {
        setTitle("Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        //create non-editable text are
        textArea = new JTextArea();
        textArea.setEditable(false);

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);

        startServer();
    }

    private void startServer() {
        try {
        	//create server socket at port 4228
            serverSocket = new ServerSocket(4228);
            appendToTextArea("Server started. Listening on port 4228.");

            //wait for client connection
            clientSocket = serverSocket.accept();
            appendToTextArea("Client connected: " + clientSocket.getInetAddress().getHostAddress());

            //used to send message message client
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            //used to read the message from client
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                    	//if connection established, read message
                        String message = reader.readLine();
                        appendToTextArea("Client: " + message);
                        processMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //process the message. 1.count the word 2.send the message to client
    private void processMessage(String message) {
        int wordCount = countWords(message);
        sendMessage(Integer.toString(wordCount));
    }

    private int countWords(String message) {
        // Split the string into words based on whitespace
        String[] words = message.split("\\s+");

        // Return the length of the array, which represents the number of words
        return words.length;
    }


    private void sendMessage(String message) {
    	//wrap the message
        writer.println(message);
    }

    private void appendToTextArea(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.append(message + "\n");
            }
        });
    }
}

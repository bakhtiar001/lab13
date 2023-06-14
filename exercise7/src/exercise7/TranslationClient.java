package exercise7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class TranslationClient extends JFrame {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 4228;

    private JTextField textInput;
    private JComboBox<String> languageComboBox;
    private JTextArea translatedTextOutput;

    public TranslationClient() {
        setTitle("Text Translation Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel textLabel = new JLabel("Enter English text:");
        textInput = new JTextField(20);

        JLabel languageLabel = new JLabel("Target Language:");
        languageComboBox = new JComboBox<>(new String[]{"Bahasa Malaysia", "Arabic", "Korean"});

        JButton translateButton = new JButton("Translate");
        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                translateText();
            }
        });

        inputPanel.add(textLabel);
        inputPanel.add(textInput);
        inputPanel.add(languageLabel);
        inputPanel.add(languageComboBox);
        inputPanel.add(translateButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        translatedTextOutput = new JTextArea();
        translatedTextOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(translatedTextOutput);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }

    private void translateText() {
        String text = textInput.getText();
        String targetLanguage = (String) languageComboBox.getSelectedItem();

        try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            writer.println(text);
            writer.println(targetLanguage);

            String translatedText = reader.readLine();
            translatedTextOutput.setText(translatedText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TranslationClient();
            }
        });
    }
}

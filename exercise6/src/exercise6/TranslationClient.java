package exercise6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TranslationClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 4228;

    public static void main(String[] args) {
        try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("Enter English text: ");
            String text = userInputReader.readLine();

            System.out.print("Enter target language (Bahasa Malaysia, Arabic, Korean): ");
            String targetLanguage = userInputReader.readLine();

            writer.println(text);
            writer.println(targetLanguage);

            String translatedText = reader.readLine();
            System.out.println("Translated text: " + translatedText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

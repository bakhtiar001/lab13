package exercise6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TranslationServer {
    private static final int PORT = 4228;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                    String text = reader.readLine();
                    String targetLanguage = reader.readLine();

                    String translatedText = translateText(text, targetLanguage);
                    writer.println(translatedText);

                    reader.close();
                    writer.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String translateText(String text, String targetLanguage) {
        if (targetLanguage.equalsIgnoreCase("Bahasa Malaysia")) {
            if (text.equalsIgnoreCase("Good morning")) {
                return "Selamat pagi";
            } else if (text.equalsIgnoreCase("Good night")) {
                return "Selamat malam";
            } else if (text.equalsIgnoreCase("How are you?")) {
                return "Apa khabar?";
            } else if (text.equalsIgnoreCase("Thank you")) {
                return "Terima kasih";
            } else if (text.equalsIgnoreCase("Goodbye")) {
                return "Selamat tinggal";
            } else if (text.equalsIgnoreCase("What’s up?")) {
                return "Ada apa?";
            }
        } else if (targetLanguage.equalsIgnoreCase("Arabic")) {
            if (text.equalsIgnoreCase("Good morning")) {
                return "الخير صباح";
            } else if (text.equalsIgnoreCase("Good night")) {
                return "مساؤك طاب";
            } else if (text.equalsIgnoreCase("How are you?")) {
                return "حالك؟ كيف";
            } else if (text.equalsIgnoreCase("Thank you")) {
                return "لك شكرا";
            } else if (text.equalsIgnoreCase("Goodbye")) {
                return "السالمة مع";
            } else if (text.equalsIgnoreCase("What’s up?")) {
                return "أخبارك؟ ما";
            }
        } else if (targetLanguage.equalsIgnoreCase("Korean")) {
            if (text.equalsIgnoreCase("Good morning")) {
                return "좋은 아침";
            } else if (text.equalsIgnoreCase("Good night")) {
                return "안녕히 주무세요";
            } else if (text.equalsIgnoreCase("How are you?")) {
                return "어떻게 지내세요?";
            } else if (text.equalsIgnoreCase("Thank you")) {
                return "감사합니다";
            } else if (text.equalsIgnoreCase("Goodbye")) {
                return "안녕";
            } else if (text.equalsIgnoreCase("What’s up?")) {
                return "뭐야?";
            }
        }

        return "Translation not available";
    }
}

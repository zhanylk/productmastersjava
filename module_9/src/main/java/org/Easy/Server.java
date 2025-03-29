package src.main.java.org.Easy;

import java.io.*;
import java.net.*;
import java.time.LocalTime;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер запущен. Ожидание подключения...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Клиент подключен: " + clientSocket.getInetAddress());

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Клиент: " + inputLine);

                        if ("/time".equalsIgnoreCase(inputLine.trim())) {
                            String currentTime = LocalTime.now().toString();
                            out.println(currentTime);
                            System.out.println("Сервер отправил: " + currentTime);
                        } else {
                            out.println("Неизвестная команда. Используйте /time");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Ошибка при работе с клиентом: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер: " + e.getMessage());
        }
    }
}
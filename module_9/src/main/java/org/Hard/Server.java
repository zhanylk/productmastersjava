package src.main.java.org.Hard;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private static final int TIMEOUT_SECONDS = 30;
    private static final Map<String, String> USERS = new HashMap<>();

    static {

        USERS.put("user1", "1234");
        USERS.put("user2", "qwerty");
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер запущен. Ожидание подключения...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {


            out.println("Введите логин:");
            String login = in.readLine();

            out.println("Введите пароль:");
            String password = in.readLine();

            if (!authenticate(login, password)) {
                out.println("Ошибка аутентификации");
                clientSocket.close();
                return;
            }

            out.println("Добро пожаловать, " + login + "!");
            System.out.println("Клиент " + login + " подключен: " + clientSocket.getInetAddress());


            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("/time".equalsIgnoreCase(inputLine.trim())) {
                    out.println(LocalTime.now());
                } else {
                    out.println("Неизвестная команда");
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка с клиентом: " + e.getMessage());
        }
    }

    private static boolean authenticate(String login, String password) {
        return USERS.containsKey(login) && USERS.get(login).equals(password);
    }
}

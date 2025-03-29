package src.main.java.org.Medium;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    private static final int TIMEOUT_SECONDS = 30;

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
        ScheduledExecutorService timeoutExecutor = Executors.newSingleThreadScheduledExecutor();
        AtomicBoolean isActive = new AtomicBoolean(true);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            System.out.println("Клиент подключен: " + clientSocket.getInetAddress());

            ScheduledFuture<?> timeoutTask = timeoutExecutor.schedule(() -> {
                if (isActive.get()) {
                    System.out.println("Разрыв соединения с клиентом " + clientSocket.getInetAddress() + " по таймауту");
                    try { clientSocket.close(); } catch (IOException ignored) {}
                }
            }, TIMEOUT_SECONDS, TimeUnit.SECONDS);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                timeoutTask.cancel(false);
                timeoutTask = timeoutExecutor.schedule(() -> {
                    if (isActive.get()) {
                        System.out.println("Разрыв соединения по таймауту");
                        try { clientSocket.close(); } catch (IOException ignored) {}
                    }
                }, TIMEOUT_SECONDS, TimeUnit.SECONDS);

                System.out.println("Клиент: " + inputLine);

                if ("/time".equalsIgnoreCase(inputLine.trim())) {
                    out.println(LocalTime.now());
                } else {
                    out.println("Неизвестная команда");
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка с клиентом " + clientSocket.getInetAddress() + ": " + e.getMessage());
        } finally {
            isActive.set(false);
            timeoutExecutor.shutdown();
        }
    }
}
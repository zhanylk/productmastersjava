package src.main.java.org.Medium;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Client {
    private static final int TIMEOUT_SECONDS = 30;

    public static void main(String[] args) {
        ScheduledExecutorService timeoutExecutor = Executors.newSingleThreadScheduledExecutor();
        AtomicBoolean isActive = new AtomicBoolean(true);
        AtomicReference<ScheduledFuture<?>> timeoutTaskRef = new AtomicReference<>();

        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Подключено к серверу. Введите команду (/time):");


            timeoutTaskRef.set(timeoutExecutor.schedule(() -> {
                if (isActive.get()) {
                    System.out.println("Сервер не отвечает. Разрыв соединения.");
                    try { socket.close(); } catch (IOException ignored) {}
                    System.exit(0);
                }
            }, TIMEOUT_SECONDS, TimeUnit.SECONDS));

            new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        timeoutTaskRef.get().cancel(false);
                        timeoutTaskRef.set(timeoutExecutor.schedule(() -> {
                            if (isActive.get()) {
                                System.out.println("Сервер не отвечает. Разрыв соединения.");
                                try { socket.close(); } catch (IOException ignored) {}
                                System.exit(0);
                            }
                        }, TIMEOUT_SECONDS, TimeUnit.SECONDS));

                        System.out.println("Сервер: " + response);
                    }
                } catch (IOException e) {
                    if (isActive.get()) {
                        System.err.println("Соединение с сервером разорвано: " + e.getMessage());
                    }
                }
            }).start();

            while (true) {
                String userInput = scanner.nextLine();
                out.println(userInput);

                timeoutTaskRef.get().cancel(false);
                timeoutTaskRef.set(timeoutExecutor.schedule(() -> {
                    if (isActive.get()) {
                        System.out.println("Сервер не отвечает. Разрыв соединения.");
                        try { socket.close(); } catch (IOException ignored) {}
                        System.exit(0);
                    }
                }, TIMEOUT_SECONDS, TimeUnit.SECONDS));
            }

        } catch (IOException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        } finally {
            isActive.set(false);
            timeoutExecutor.shutdown();
        }
    }
}
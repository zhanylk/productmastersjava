import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {


            System.out.println(in.readLine());
            String login = scanner.nextLine();
            out.println(login);

            System.out.println(in.readLine());
            String password = scanner.nextLine();
            out.println(password);


            String authResponse = in.readLine();
            System.out.println(authResponse);

            if (authResponse.startsWith("Ошибка")) {
                return;
            }


            while (true) {
                String userInput = scanner.nextLine();
                out.println(userInput);

                String response = in.readLine();
                System.out.println("Сервер: " + response);
            }
        } catch (IOException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        }
    }
}
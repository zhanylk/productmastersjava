package five.easy;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Введите номер дня недели (1-7): ");
    int dayNumber = scanner.nextInt();
    scanner.close();

    try {
      System.out.println("Вы выбрали: " + DayOfWeek.getDay(dayNumber));
    } catch (IllegalArgumentException e) {
      System.out.println("Ошибка: " + e.getMessage());
    }
  }
}
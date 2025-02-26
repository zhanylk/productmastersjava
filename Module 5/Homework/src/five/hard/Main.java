package five.hard;

import java.util.ArrayList;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество чисел: ");
        int n = scanner.nextInt();

        ArrayList<Integer> numbers = new ArrayList<>();
        System.out.println("Введите " + n + " чисел:");
        for (int i = 0; i < n; i++) {
            numbers.add(scanner.nextInt());
        }
        scanner.close();

        ArrayList<Integer> uniqueNumbers = removeDuplicates(numbers);
        System.out.println("Список без дубликатов: " + uniqueNumbers);
    }

    public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
        Set<Integer> seen = new HashSet<>();
        ArrayList<Integer> result = new ArrayList<>();

        for (Integer num : list) {
            if (!seen.contains(num)) {
                seen.add(num);
                result.add(num);
            }
        }
        return result;
    }
}
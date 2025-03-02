package six.medium;

import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    String filePath = "words.txt";

    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      HashSet<String> uniqueWords = new HashSet<>();
      HashMap<String, Integer> wordCount = new HashMap<>();

      String line;
      while ((line = reader.readLine()) != null) {
        String[] words = line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
        for (String word : words) {
          if (!word.isEmpty()) {
            uniqueWords.add(word);
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
          }
        }
      }
      reader.close();

      System.out.println("Уникальные слова: " + uniqueWords);
      System.out.println("Частота слов:");
      for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
      }
    } catch (IOException e) {
      System.out.println("Ошибка при чтении файла: " + e.getMessage());
    }
  }
}
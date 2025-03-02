package six.hard;

import java.util.*;

public class Main {
  public static void main(String[] args) {
    SafeList<String> list = new SafeList<>();
    list.add("apple");
    list.add("banana");
    list.add(null); // Не добавляется
    list.add("apple"); // Не добавляется

    System.out.println(list.get(0)); // apple
    System.out.println(list.get(5)); // null (не ошибка!)
  }

  public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
    return new ArrayList<>(new LinkedHashSet<>(list));
  }
}

class SafeList<T> extends ArrayList<T> {
  @Override
  public boolean add(T element) {
    if (element == null || contains(element)) return false;
    return super.add(element);
  }

  @Override
  public T get(int index) {
    return (index >= 0 && index < size()) ? super.get(index) : null;
  }
}

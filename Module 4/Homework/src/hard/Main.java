package hard;

public class Main {

  public static void main(String[] args) {

    Box<Integer> intBox = new Box<>(10);
    intBox.showType();
    System.out.println("Значение: " + intBox.getItem());

    Box<String> strBox = new Box<>("Hello");
    strBox.showType();
    System.out.println("Значение: " + strBox.getItem());
  }

}

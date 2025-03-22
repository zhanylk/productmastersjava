package easy;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> people = List.of(
                Person.builder().name("Alice").age(25).city("New York").build(),
                Person.builder().name("Bob").age(30).city("Los Angeles").build(),
                Person.builder().name("Charlie").age(22).city("Chicago").build()
        );

        // Вывод списка
        people.forEach(System.out::println);
    }
}
package medium;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> people = List.of(
                Person.builder().name("Alice").age(25).city("New York").build(),
                Person.builder().name("Bob").age(30).city("Almaty").build(),
                Person.builder().name("Charlie").age(17).city("Chicago").build(),
                Person.builder().name("David").age(40).city("Almaty").build(),
                Person.builder().name("Eve").age(20).city("Berlin").build()
        );


        List<Person> adults = people.stream()
                .filter(person -> person.getAge() > 18)
                .collect(Collectors.toList());
        System.out.println("Взрослые: " + adults);


        double averageAge = people.stream()
                .mapToInt(Person::getAge)
                .average()
                .orElse(0);
        System.out.println("Средний возраст: " + averageAge);


        List<String> almatyPeople = people.stream()
                .filter(person -> "Almaty".equals(person.getCity()))
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("Люди из Алматы: " + almatyPeople);


        Map<String, Integer> nameToAgeMap = people.stream()
                .collect(Collectors.toMap(Person::getName, Person::getAge));
        System.out.println("Карта (Имя → Возраст): " + nameToAgeMap);
    }
}

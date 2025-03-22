package hard;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> people = List.of(
                Person.builder().name("Alice").age(25).city("New York").build(),
                Person.builder().name("Bob").age(30).city("Almaty").build(),
                Person.builder().name("Charlie").age(17).city("Chicago").build(),
                Person.builder().name("David").age(40).city("Almaty").build(),
                Person.builder().name("Eve").age(20).city("Berlin").build(),
                Person.builder().name("Frank").age(50).city("London").build(),
                Person.builder().name("Grace").age(45).city("Paris").build()
        );


        List<Person> top3Oldest = people.stream()
                .sorted(Comparator.comparingInt(Person::getAge).reversed())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("ТОП-3 самых старших: " + top3Oldest);


        Company company1 = Company.builder()
                .name("Google")
                .employees(List.of(people.get(0), people.get(1), people.get(2)))
                .build();

        Company company2 = Company.builder()
                .name("Microsoft")
                .employees(List.of(people.get(3), people.get(4), people.get(5)))
                .build();

        Company company3 = Company.builder()
                .name("Amazon")
                .employees(List.of(people.get(6)))
                .build();

        List<Company> companies = List.of(company1, company2, company3);


        Map<String, List<Person>> filteredEmployees = companies.stream()
                .collect(Collectors.toMap(
                        Company::getName,
                        company -> company.getEmployees().stream()
                                .filter(person -> person.getAge() > 25)
                                .collect(Collectors.toList())
                ));
        System.out.println("Сотрудники старше 25 лет: " + filteredEmployees);


        Map<String, Double> companyAverageAge = companies.stream()
                .collect(Collectors.toMap(
                        Company::getName,
                        company -> company.getEmployees().stream()
                                .mapToInt(Person::getAge)
                                .average()
                                .orElse(0.0)
                ));
        System.out.println("Средний возраст сотрудников в компаниях: " + companyAverageAge);
    }
}
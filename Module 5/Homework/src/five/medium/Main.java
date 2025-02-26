package five.medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<DayOfWeek> daysOfWeek = new ArrayList<>();


        Collections.addAll(daysOfWeek, DayOfWeek.values());


        System.out.println("Все дни недели:");
        for (DayOfWeek day : daysOfWeek) {
            System.out.println(day);
        }


        System.out.println("\nПроверка выходных:");
        for (DayOfWeek day : daysOfWeek) {
            System.out.println(day + " - выходной? " + isWeekend(day));
        }
    }

    public static boolean isWeekend(DayOfWeek day) {
        return day == DayOfWeek.суббота || day == DayOfWeek.воскресенье;
    }
}

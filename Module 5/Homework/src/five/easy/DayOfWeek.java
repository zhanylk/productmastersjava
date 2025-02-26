package five.easy;

public enum DayOfWeek {
    понедельник,
    вторник,
    среда,
    четверг,
    пятница,
    суббота,
    воскресенье;

    public static DayOfWeek getDay(int dayNumber) {
        if (dayNumber < 1 || dayNumber > 7) {
            throw new IllegalArgumentException("Номер дня должен быть от 1 до 7");
        }
        return DayOfWeek.values()[dayNumber - 1];
    }
}

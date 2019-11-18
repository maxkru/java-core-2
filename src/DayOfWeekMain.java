public class DayOfWeekMain {

    public static void main(final String[] args) {
        System.out.println(getWorkingHours(DayOfWeek.SATURDAY));
    }

    public static String getWorkingHours(DayOfWeek day) {
        String result;
        if (day.getRemainingHours() > 0)
            result = Integer.toString(day.getRemainingHours());
        else
            result = "Сегодня выходной!";
        return result;
    }
}
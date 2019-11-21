import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Lesson3Main {

    private static String[] words = {
            "strict",
            "exploit",
            "keg",
            "keg",
            "exploit",
            "able",
            "strict",
            "keg",
            "luminance",
            "siren",
            "luminance",
            "trace",
            "strict",
            "dubbed",
            "captive",
            "keg",
            "trace"};

    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = Lesson3Task1.stringOccurrences(words);

        // использую TreeMap - хочу, чтобы сортировались в алфавитном порядке
        TreeMap<String, Integer> treeMap = new TreeMap<>(hashMap);
        for (Map.Entry<String, Integer> entry : treeMap.entrySet())
            System.out.println(entry.getKey() + ": " + entry.getValue());

        PhoneBook phoneBook = new PhoneBook(", ");

        phoneBook.add("Иванов", "+79012345678", "+79123456789");
        phoneBook.add("Иванов", "+79123456789");
        phoneBook.add("Петров", "+79123456789");
        phoneBook.add("Сидоров");   // допустимо добавлять имя, не добавляя номера

        System.out.println("Андреев: " + phoneBook.get("Андреев"));
        System.out.println("Петров: " + phoneBook.get("Петров"));
        System.out.println("Сидоров: " + phoneBook.get("Сидоров"));
        System.out.println("Иванов: " + phoneBook.get("Иванов"));

    }

}

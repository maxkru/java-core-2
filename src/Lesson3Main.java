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
    }

}

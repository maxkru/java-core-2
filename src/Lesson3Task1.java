
import java.util.HashMap;

public class Lesson3Task1 {

    static HashMap<String, Integer> stringOccurrences(String[] stringArr) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String word: stringArr)
            map.put(word, map.containsKey(word) ? map.get(word) + 1 : 1);
        return map;
    }

}

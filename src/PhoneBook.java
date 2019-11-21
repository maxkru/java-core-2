
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;

public class PhoneBook {
    public static final String NUMBER_SEPARATOR = ", ";

    private HashMap<String, HashSet<String>> book;

    public PhoneBook() {
        this.book = new HashMap<>();
    }

    public void add(String name, String... numbers) {

        if( !book.containsKey(name) )
            book.put(name, new HashSet<>());
        book.get(name).addAll(Arrays.asList(numbers));
    }

    public String get(String name) {
        // возвращает null, если имени нет в книге
        // возвращает "", если имя есть в книге, но нет номеров для него
        // иначе возвращает строку из номеров, разделённых ", " (в конце строки ", " нет)

        StringBuilder result = null;

        if(book.containsKey(name)) {
            result = new StringBuilder();

            HashSet<String> numbers = book.get(name);
            Iterator<String> iterator = numbers.iterator();
            while(iterator.hasNext()) {
                result.append(iterator.next()).append(NUMBER_SEPARATOR);
            }

            if(result.length()>0)
                result.delete(result.length()-NUMBER_SEPARATOR.length(), result.length()-NUMBER_SEPARATOR.length()+1);
        }

        return (result == null ? null : result.toString());
    }

}

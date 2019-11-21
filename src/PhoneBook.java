
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;

public class PhoneBook {

    private String numberSeparator; // то, чем будут разделяться номера в get(name)
    private HashMap<String, HashSet<String>> book;

    public PhoneBook(String numberSeparator) {
        this.book = new HashMap<>();
        this.numberSeparator = numberSeparator;
    }

    public void add(String name, String... numbers) {

        if( !book.containsKey(name) )
            book.put(name, new HashSet<>());
        book.get(name).addAll(Arrays.asList(numbers));
    }

    public String get(String name) {
        // возвращает null, если имени нет в книге
        // возвращает "", если имя есть в книге, но нет номеров для него
        // иначе возвращает строку из номеров, разделённых numberSeparator (в конце строки numberSeparator нет)

        StringBuilder result = null;

        if(book.containsKey(name)) {
            result = new StringBuilder();

            HashSet<String> numbers = book.get(name);
            Iterator<String> iterator = numbers.iterator();
            while(iterator.hasNext()) {
                result.append(iterator.next()).append(numberSeparator);
            }

            if(result.length()>0 && numberSeparator.length() > 0)
                result.delete(result.length() - numberSeparator.length(), result.length() - numberSeparator.length() + 1);
        }

        return (result == null ? null : result.toString());
    }

    public String getNumberSeparator() {
        return numberSeparator;
    }

    public void setNumberSeparator(String numberSeparator) {
        this.numberSeparator = numberSeparator;
    }
}

import java.util.Scanner;
import java.util.regex.Pattern;

public class PasswordRegExp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Password?: ");
        System.out.println(checkPasswordValidity(scanner.nextLine()));
    }

    static boolean checkPasswordValidity(String password) {
        // пароль длиной >= 8, состоит из букв и цифр. Минимум 1 цифра, 1 строчная и 1 прописная
        return Pattern.matches("^(?=.*\\d)(?=.*\\p{Lower})(?=.*\\p{Upper})[\\d\\p{Lower}\\p{Upper}]{8,}$", password);
    }
}

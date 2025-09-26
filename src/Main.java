import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        Scanner scanner = new Scanner(System.in);
        String username = System.getProperty("user.name");
        String hostname = InetAddress.getLocalHost().getHostName();
        boolean exit = false;

        while (!exit) {
            System.out.print(username + "@" + hostname + ":~$ ");
            String line = scanner.nextLine();

            String[] tokens = tokenize(line);
            if (tokens.length == 0) continue; // пустая строка

            String command = tokens[0];
            String[] arguments = Arrays.copyOfRange(tokens, 1, tokens.length);

            switch (command) {
                case "ls":
                    System.out.println("Команда: " + command);
                    System.out.print("Аргументы: ");
                    for (String arg : arguments) System.out.println(arg + " ");
                    System.out.println();
                    break;
                case "cd":
                    System.out.println("Команда: " + command);
                    System.out.print("Аргументы: ");
                    for (String arg : arguments) System.out.println(arg + " ");
                    System.out.println();
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println(command + " не является внешней или внутренней командой");
            }
        }
        scanner.close();
    }

    // Простая функция-токенизатор:
    // - поддерживает "кавычки"
    // - поддерживает экранирование через обратный слэш: \" \\ \n и т.д.
    public static String[] tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '\\' && i + 1 < input.length()) {
                // экранирование: добавляем следующий символ буквально
                i++;
                cur.append(input.charAt(i));
            } else if (c == '"') {
                // открытие/закрытие кавычек
                inQuotes = !inQuotes;
            } else if (Character.isWhitespace(c) && !inQuotes) {
                // разделитель между аргументами (только если мы не внутри кавычек)
                if (cur.length() > 0) {
                    tokens.add(cur.toString());
                    cur.setLength(0);
                }
            } else {
                cur.append(c);
            }
        }

        // добавляем последний токен если есть
        if (cur.length() > 0) tokens.add(cur.toString());
        return tokens.toArray(new String[0]);
    }
}
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner( System.in );
        ArrayList<String> strings = new ArrayList<>();

        while (true) {
            System.out.println( "Выберите действие:" );
            System.out.println( "1. Добавить строку" );
            System.out.println( "2. Удалить строку" );
            System.out.println( "3. Поиск одинаковых элементов" );
            System.out.println( "4. Выгрузка в XML-файл" );
            System.out.println( "5. Реверс строк" );
            System.out.println( "6. Статистика по символам" );
            System.out.println( "7. Поиск подстроки" );
            System.out.println( "8. Инициализация списка из файла и вывод содержимого" );
            System.out.println( "9. Сравнение элементов списка" );
            System.out.println( "10. Подсчет длин строк и вывод в упорядоченном виде" );
            System.out.println( "11. Добавление с ограничением размерности" );

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println( "Введите строку для добавления:" );
                    String newString = scanner.nextLine();
                    strings.add( newString );
                    break;
                case 2:
                    System.out.println( "Введите индекс строки для удаления:" );
                    int index = scanner.nextInt();
                    strings.remove( index );
                    break;
                case 3:
                    List<String> duplicates = findDuplicates( strings );
                    System.out.println( "Повторяющиеся элементы: " + duplicates );
                    break;
                case 4:
                    exportToXML( strings );
                    System.out.println( "Данные успешно выгружены в XML-файл." );
                    break;
                case 5:
                    System.out.println( "Результат реверса строки: " + reverseString( strings.get( strings.size() - 1 ) ) );
                    break;
                case 6:
                    Map<Character, Integer> charStats = getCharacterStatistics( strings.get( strings.size() - 1 ) );
                    System.out.println( "Статистика: " + charStats );
                    break;
                case 7:
                    System.out.println( "Введите подстроку для поиска:" );
                    String subString = scanner.nextLine();
                    if (substringSearch.isSubstringPresent( strings.get( strings.size() - 1 ), subString )) {
                        System.out.println( "Подстрока найдена." );
                    } else {
                        System.out.println( "Подстрока не найдена." );
                    }
                    break;
                case 8:
                    List<String> fileData = FileInitializer.initializeListFromFile( "input.txt" );
                    System.out.println( "Данные из файла: " + fileData );
                    break;
                case 9:
                    for (int i = 0; i < strings.size() - 1; i++) {
                        String current = strings.get( i );
                        String next = strings.get( i + 1 );
                        if (current.equals( next )) {
                            System.out.println( "Элементы " + current + " и " + next + " одинаковы." );
                        } else {
                            System.out.println( "Элементы " + current + " и " + next + " разные." );
                        }
                    }
                    break;
                case 10:
                    Map<Integer, List<String>> lengthStatistics = LineLengthCounter.countAndSortByLength( strings );
                    System.out.println( "Статистика длин строк: " + lengthStatistics );
                    break;
                case 11:
                    System.out.println( "Введите новую строку:" );
                    String newStr = scanner.nextLine();
                    BoundedListAdder.addWithLimit( strings, newStr, 5 );
                    break;
                default:
                    System.out.println( "Неправильный выбор. Попробуйте снова." );
            }
        }
    }

    public static List<String> findDuplicates(List<String> list) {
        List<String> duplicates = new ArrayList<>();
        Map<String, Integer> elementCount = new HashMap<>();

        for (String element : list) {
            if (elementCount.containsKey( element )) {
                elementCount.put( element, elementCount.get( element ) + 1 );
            } else {
                elementCount.put( element, 1 );
            }
        }

        for (Map.Entry<String, Integer> entry : elementCount.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.add( entry.getKey() );
            }
        }

        return duplicates;
    }

    public static void exportToXML(List<String> data) {
        try {
            File file = new File( "output.xml" );
            FileWriter writer = new FileWriter( file );

            writer.write( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" );
            writer.write( "<data>\n" );
            for (String item : data) {
                writer.write( "  <item>" + item + "</item>\n" );
            }
            writer.write( "</data>" );

            writer.close();
            System.out.println( "Данные успешно выгружены в XML-файл." );
        } catch (IOException e) {
            System.out.println( "Ошибка при записи в файл." );
            e.printStackTrace();
        }
    }

    public static String reverseString(String str) {
        StringBuilder reversed = new StringBuilder( str ).reverse();
        return reversed.toString();
    }

    public static Map<Character, Integer> getCharacterStatistics(String str) {
        Map<Character, Integer> charStats = new HashMap<>();
        for (char c : str.toCharArray()) {
            charStats.put( c, charStats.getOrDefault( c, 0 ) + 1 );
        }
        return charStats;
    }
    // Метод для поиска подстроки в строке
    public static boolean substringSearch(String text, String pattern) {
        return text.contains(pattern);
    }

    // Метод для инициализации файла
    public static void initializeFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("Файл " + fileName + " успешно создан.");
            } else {
                System.out.println("Файл " + fileName + " уже существует.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла " + fileName);
            e.printStackTrace();
        }
    }

    // Метод для подсчета длин строк и вывода в упорядоченном виде
    public static Map<Integer, List<String>> countAndSortByLength(List<String> strings) {
        Map<Integer, List<String>> lengthStatistics = new HashMap<>();
        for (String str : strings) {
            int length = str.length();
            if (!lengthStatistics.containsKey(length)) {
                lengthStatistics.put(length, new ArrayList<>());
            }
            lengthStatistics.get(length).add(str);
        }
        return lengthStatistics;
    }

    // Метод для добавления элемента с ограничением размерности
    public static void addWithLimit(List<String> strings, String newStr, int limit) {
        if (strings.size() < limit) {
            strings.add(newStr);
        } else {
            System.out.println("Достигнуто максимальное количество элементов в списке.");
        }
    }
}


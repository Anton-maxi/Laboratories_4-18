package ua.sumdu.zboryk.lab7;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Консольний драйвер програми: меню для створення одягу, виведення складу, демонстрації копіювання й лічильника створених об'єктів {@link Clothes}
 * {@link Clothes} з обробкою некоректного введення.
 */
public class Main {

    /**
     * Зчитує з клавіатури ціле число в діапазоні від 1 до 6 (пункт меню).
     * Запит повторюється, доки введення не стане коректним.
     * @param scanner сканер, з якого читається введення
     * @return пункт меню від 1 до 6
     */
    public static int readNumber(Scanner scanner) {
        String strNum;
        int num;
        while (true) {
            try {
                strNum = scanner.nextLine();
                num = Integer.parseInt(strNum.trim());
                if (num < 1 || num > 6){
                    System.out.print("Помилка! Введіть ціле число в діапазоні 1-6 (включно): ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Помилка! Введено не число! Повторіть спробу: ");
            }
        }
        return num;
    }

    /**
     * Зчитує рядок і передає його сетеру. Якщо сетер кидає
     * {@link InvalidClothesDataException}, виводить повідомлення й повторює запит.
     * @param scanner сканер, з якого читається введення
     * @param prompt  підказка, що виводиться перед введенням
     * @param setter  сетер поля, який приймає введений рядок
     */
    static void readStringField(Scanner scanner, String prompt, Consumer<String> setter){
        while (true){
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                setter.accept(line);
                break;
            }
            catch (InvalidClothesDataException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Показує нумерований список значень перерахування, зчитує номер вибраного значення
     * й передає його сетеру. Нечислове введення та номер поза списком повідомляються
     * користувачу, а запит повторюється.
     * @param scanner сканер, з якого читається введення
     * @param prompt  підказка, що виводиться перед списком
     * @param values  масив значень перерахування для вибору (результат {@code values()})
     * @param setter  сетер поля, який приймає вибране значення
     * @param <E>     тип перерахування
     */
    static <E extends Enum<E>> void readEnumField(Scanner scanner, String prompt, E[] values, Consumer<E> setter) {
        while (true){
            System.out.println(prompt);
            for(int i=0; i< values.length; i+=1){
                System.out.println((i+1)+". "+values[i]);
            }
            String line = scanner.nextLine();
            try {
                int num=Integer.parseInt(line.trim());
                if (num<1 || num> values.length){
                    System.out.println("Помилка! Введіть числове значення від 1 до "+values.length+"!");
                    continue;
                }
                setter.accept(values[num - 1]);
                break;
            }
            catch (InvalidClothesDataException e){
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException e){
                System.out.println("Помилка! Введіть числове значення!");
            }
        }
    }

    /**
     * Зчитує число, перетворює його за допомогою парсера й передає сетеру. Нечислове
     * введення та значення, відхилені сетером, повідомляються користувачу, а запит повторюється.
     * @param scanner сканер, з якого читається введення
     * @param prompt  підказка, що виводиться перед введенням
     * @param parser  функція перетворення рядка на число
     * @param setter  сетер поля, який приймає число
     * @param <T>     числовий тип значення
     */
    static <T extends Number> void readNumberField(Scanner scanner, String prompt, Function<String, T> parser, Consumer<T> setter){
        while (true){
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                setter.accept(parser.apply(line.trim()));
                break;
            }
            catch (InvalidClothesDataException e){
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException e){
                System.out.println("Помилка! Введіть числове значення!");
            }
        }
    }

    /**
     * Точка входу: показує меню та виконує вибрані дії, доки користувач не завершить роботу.
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClothesStore store = new ClothesStore("Магазин одягу");
        boolean run = true;
        while (true){
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║              МАГАЗИН ОДЯГУ               ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.println("║ 1. Створити новий об'єкт                 ║");
            System.out.println("║ 2. Вивести всі об'єкти                   ║");
            System.out.println("║ 3. Завершити роботу                      ║");
            System.out.println("║ 4. Демонстрація копіювання               ║");
            System.out.println("║ 5. Кількість створених об'єктів          ║");
            System.out.println("║ 6. Загальна кількість одиниць на складі  ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("Ваш вибір: ");
            int choice = readNumber(scanner);
            switch (choice){
                case 1:
                    System.out.print("Введіть властивості для Одягу "+ (store.getSize()+1)+ ":\n");
                    Clothes item = new Clothes();
                    readStringField(scanner, "Назва: ", item::setName);
                    readStringField(scanner, "Матеріал: ", item::setMaterial);
                    readEnumField(scanner, "Розмір:", ClothingSize.values(), item::setSize);
                    readEnumField(scanner, "Стать:", Gender.values(), item::setSexType);
                    readStringField(scanner, "Бренд: ", item::setBrand);
                    readNumberField(scanner, "Ціна (через крапку): ", Double::parseDouble, item::setPrice);
                    readNumberField(scanner, "Кількість: ", Integer::parseInt, item::setQuantity);
                    store.addItem(item);
                    break;
                case 2:
                    if (store.isEmpty()){
                        System.out.println("Помилка! Список порожній!");
                        break;
                    }
                    System.out.print(store);
                    break;
                case 3:
                    run = false;
                    break;
                case 4:
                    if (store.isEmpty()){
                        System.out.println("Помилка! Список порожній!");
                        break;
                    }
                    Clothes copy = new Clothes(store.getItem(0));
                    System.out.println("Чи однакові об'єкти copy та перший: "+ store.getItem(0).equals(copy));
                    System.out.println("Чи copy == перший: "+ (copy == store.getItem(0)));
                    if (store.getSize() > 1) {
                        System.out.println("Перший одяг equals другий: " + store.getItem(0).equals(store.getItem(1)));
                    }
                    copy.setName("Змінена");
                    System.out.println("\nНазва змінена!");
                    System.out.println("Чи однакові об'єкти copy та перший: "+ store.getItem(0).equals(copy));
                    System.out.println("Чи copy == перший: "+ (copy == store.getItem(0)));
                    if (store.getSize() > 1) {
                        System.out.println("Перший одяг equals другий: " + store.getItem(0).equals(store.getItem(1)));
                    }

                    break;
                case 5:
                    System.out.println("Створено об'єктів Clothes: " + Clothes.getCount());
                    break;
                case 6:
                    System.out.println("Загальна кількість одиниць одягу на складі: " + store.getTotalQuantity());
                    break;
            }
            if (!run){
                break;
            }
        }
    }
}

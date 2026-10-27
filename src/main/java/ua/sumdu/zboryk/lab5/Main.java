package ua.sumdu.zboryk.lab5;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Консольний драйвер програми: меню для створення, виведення й порівняння об'єктів
 * {@link Clothes} з обробкою некоректного введення.
 */
public class Main {

    /**
     * Зчитує з клавіатури ціле число в діапазоні від 1 до 4 (пункт меню).
     * Запит повторюється, доки введення не стане коректним.
     * @param scanner сканер, з якого читається введення
     * @return пункт меню від 1 до 4
     */
    public static int readNumber(Scanner scanner) {
        String strNum;
        int num;
        while (true) {
            try {
                strNum = scanner.nextLine();
                num = Integer.parseInt(strNum.trim());
                if (num < 1 || num > 4){
                    System.out.print("Помилка! Введіть ціле число в діапазоні 1-4 (включно): ");
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
        ArrayList<Clothes> clothes = new ArrayList<>();
        boolean run = true;
        while (true){
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║                 МАГАЗИН ОДЯГУ                ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║ 1. Створити новий об'єкт                     ║");
            System.out.println("║ 2. Вивести всі об'єкти                       ║");
            System.out.println("║ 3. Завершити роботу                          ║");
            System.out.println("║ 4. Демонстрація equals та == (4 лабораторна) ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("Ваш вибір: ");
            int choice = readNumber(scanner);
            switch (choice){
                case 1:
                    System.out.print("Введіть властивості для Одягу "+ (clothes.size()+1)+ ":\n");
                    Clothes item = new Clothes();
                    readStringField(scanner, "Назва: ", item::setName);
                    readStringField(scanner, "Матеріал: ", item::setMaterial);
                    readStringField(scanner, "Розмір: ", item::setSize);
                    readStringField(scanner, "Стать: ", item::setSexType);
                    readStringField(scanner, "Бренд: ", item::setBrand);
                    readNumberField(scanner, "Ціна (через крапку): ", Double::parseDouble, item::setPrice);
                    readNumberField(scanner, "Кількість: ", Integer::parseInt, item::setQuantity);
                    clothes.add(item);
                    break;
                case 2:
                    if (clothes.size()<=0){
                        System.out.println("Помилка! Список порожній!");
                        break;
                    }
                    for (int i=0; i < clothes.size(); i++){
                        System.out.println((i + 1) + ". " + clothes.get(i));
                    }
                    break;
                case 3:
                    run = false;
                    break;
                case 4:
                    if (clothes.size()<=0){
                        System.out.println("Помилка! Список порожній!");
                        break;
                    }
                    Clothes copy = new Clothes(clothes.getFirst().getName(), clothes.getFirst().getSize(), clothes.getFirst().getMaterial(), clothes.getFirst().getSexType(), clothes.getFirst().getPrice(), clothes.getFirst().getBrand(), clothes.getFirst().getQuantity());
                    System.out.println("Чи однакові об'єкти copy та перший: "+ clothes.getFirst().equals(copy));
                    System.out.println("Чи copy == перший: "+ (copy == clothes.getFirst()));
                    if (clothes.size() > 1) {
                        System.out.println("Перший одяг equals другий: " + clothes.getFirst().equals(clothes.get(1)));
                    }
                    break;
            }
            if (!run){
                break;
            }
        }
    }
}

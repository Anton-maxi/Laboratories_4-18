package ua.sumdu.zboryk.lab7;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.ArrayList;

/**
 * Консольний драйвер програми: меню для створення одягу, виведення списку
 * і демонстрації копіювання об'єктів {@link Clothes} з обробкою некоректного введення.
 */
public class Main {

    /**
     * Зчитує з клавіатури ціле число із заданого діапазону (пункт меню).
     * Запит повторюється, доки введення не стане коректним.
     * @param scanner сканер, з якого читається введення
     * @param min     найменше допустиме значення
     * @param max     найбільше допустиме значення
     * @return введене число від {@code min} до {@code max} включно
     */
    public static int readNumber(Scanner scanner, int min, int max) {
        String strNum;
        int num;
        while (true) {
            try {
                strNum = scanner.nextLine();
                num = Integer.parseInt(strNum.trim());
                if (num < min || num > max){
                    System.out.print("Помилка! Введіть ціле число в діапазоні "+min+"-"+max+" (включно): ");
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
     * Зчитує з клавіатури властивості, спільні для всього одягу, і задає їх об'єкту.
     * Працює з будь-яким нащадком {@link Clothes}.
     * @param scanner сканер, з якого читається введення
     * @param item    об'єкт, якому задаються властивості
     * @param name    назва типу одягу для підказки
     * @param count   порядковий номер об'єкта для підказки
     */
    static void basicField(Scanner scanner, Clothes item, String name, int count){
        System.out.print("Введіть властивості для "+name+" "+count+":\n");
        readStringField(scanner, "Назва: ", item::setName);
        readStringField(scanner, "Матеріал: ", item::setMaterial);
        readEnumField(scanner, "Розмір:", ClothingSize.values(), item::setSize);
        readEnumField(scanner, "Стать:", Gender.values(), item::setSexType);
        readStringField(scanner, "Бренд: ", item::setBrand);
        readNumberField(scanner, "Ціна (через крапку): ", Double::parseDouble, item::setPrice);
        readNumberField(scanner, "Кількість: ", Integer::parseInt, item::setQuantity);
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
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║              МАГАЗИН ОДЯГУ               ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.println("║ 1. Створити новий об'єкт                 ║");
            System.out.println("║ 2. Вивести всі об'єкти                   ║");
            System.out.println("║ 3. Завершити роботу                      ║");
            System.out.println("║ 4. Демонстрація копіювання               ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("Ваш вибір: ");
            int choice = readNumber(scanner, 1, 4);
            switch (choice){
                case 1:
                    System.out.println("1. Стандартний клас - Одяг");
                    System.out.println("2. Штани");
                    System.out.println("3. Футболка");
                    int type = readNumber(scanner, 1, 3);
                    switch (type){
                        case 1:
                            Clothes item_cl = new Clothes();
                            basicField(scanner, item_cl, "Одяг", clothes.size()+1);
                            clothes.add(item_cl);
                            break;
                        case 2:
                            Pants item_pan = new Pants();
                            basicField(scanner, item_pan, "Штани", clothes.size()+1);
                            readNumberField(scanner, "Довжина штанин (см): ", Double::parseDouble, item_pan::setLength);
                            clothes.add(item_pan);
                            break;
                        case 3:
                            Shirts item_sh = new Shirts();
                            basicField(scanner, item_sh, "Футболка", clothes.size()+1);
                            readNumberField(scanner, "Довжина рукава (см): ", Integer::parseInt, item_sh::setSleeveLength);
                            clothes.add(item_sh);
                            break;
                    }
                    break;
                case 2:
                    if (clothes.isEmpty()){
                        System.out.println("Помилка! Список порожній!");
                        break;
                    }
                    // список типу ArrayList<Clothes>: toString() викликається у реального типу кожного
                    for(int i=0; i< clothes.size(); i+=1){
                        System.out.println((i+1)+". "+ clothes.get(i));
                    }
                    break;
                case 3:
                    run = false;
                    break;
                case 4:
                    if (clothes.isEmpty()){
                        System.out.println("Помилка! Список порожній!");
                        break;
                    }
                    Clothes copy = new Clothes(clothes.get(0));
                    System.out.println("Чи однакові об'єкти copy та перший: "+ clothes.get(0).equals(copy));
                    System.out.println("Чи copy == перший: "+ (copy == clothes.get(0)));
                    if (clothes.size() > 1) {
                        System.out.println("Перший одяг equals другий: " + clothes.get(0).equals(clothes.get(1)));
                    }
                    copy.setName("Змінена");
                    System.out.println("\nНазва змінена!");
                    System.out.println("Чи однакові об'єкти copy та перший: "+ clothes.get(0).equals(copy));
                    System.out.println("Чи copy == перший: "+ (copy == clothes.get(0)));
                    if (clothes.size() > 1) {
                        System.out.println("Перший одяг equals другий: " + clothes.get(0).equals(clothes.get(1)));
                    }

                    break;
            }
            if (!run){
                break;
            }
        }
    }
}

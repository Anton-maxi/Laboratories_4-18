package ua.sumdu.zboryk.lab9;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Консольний драйвер програми: меню для створення об'єктів ієрархії одягу
 * ({@link Clothes}, {@link Pants}, {@link Shirts}, {@link Jacket}, {@link Dress})
 * та виведення всіх створених об'єктів із єдиного списку з обробкою некоректного введення.
 * Під час запуску список заповнюється з файлу {@value #FILE_NAME}, а під час завершення
 * роботи його вміст записується в цей самий файл.
 */
public class Main {
    /**
     * Назва файлу, з якого читаються об'єкти під час запуску й у який вони записуються під час завершення.
     */
    static final String FILE_NAME = "input.txt";

    static final String JSON_FILE_NAME = "input.json";

    static final String[] BASE_FIELDS = {"type", "name", "material", "brand", "sexType", "size", "price", "quantity"};

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
     * Показує вибір «Так» або «Ні», зчитує номер вибраного варіанта й передає
     * відповідне значення сетеру. Некоректне введення повідомляється користувачу,
     * а запит повторюється.
     * @param scanner сканер, з якого читається введення
     * @param prompt  підказка, що виводиться перед варіантами
     * @param setter  сетер булевого поля, який приймає вибране значення
     */
    static void readBooleanField(Scanner scanner, String prompt, Consumer<Boolean> setter){
        while (true){
            System.out.println(prompt);
            System.out.println("1. Так");
            System.out.println("2. Ні");
            System.out.print("Ваш вибір: ");
            int choice = readNumber(scanner, 1, 2);
            boolean hasHood;
            if(choice==1) hasHood=true;
            else hasHood=false;
            try {
                setter.accept(hasHood);
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
     * Питає користувача, чи зберегти щойно введений об'єкт у список.
     * @param scanner сканер, з якого читається введення
     * @return {@code true}, якщо користувач вибрав «Так»
     */
    static boolean isSave(Scanner scanner){
        System.out.println("Зберегти цей об'єкт?");
        System.out.println("1. Так");
        System.out.println("2. Ні");
        System.out.print("Ваш вибір: ");
        if(readNumber(scanner, 1, 2)==1) return true;
        return false;
    }

    /**
     * Записує у файл два рядки пояснення формату (починаються з {@code #}), порожній рядок
     * і по одному рядку {@link Clothes#toFileString()} на кожен об'єкт списку.
     * Попередній вміст файлу замінюється. Після успішного запису виводить кількість
     * збережених об'єктів, а в разі помилки введення-виведення виводить повідомлення про неї.
     * @param clothes список об'єктів для збереження
     * @param fileName назва файлу
     */
    static void saveToFile (ArrayList<Clothes> clothes, String fileName){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("# Один об'єкт на рядок, поля через \"; \": Тип; Назва; Розмір; Матеріал; Стать; Ціна; Бренд; Кількість; Власне поле (у Clothes його немає).");
            writer.newLine();
            writer.write("# Типи і власне поле: Pants (довжина штанин у см, дробове), Shirts (довжина рукава у см, ціле), Jacket (капюшон: true або false), Dress (довжина у см, ціле); розмір XS, S, M, L, XL, XXL; стать Чоловіча, Жіноча або Унісекс; ціна через крапку.");
            writer.newLine();
            writer.newLine();
            for (int i = 0; i< clothes.size(); i+=1){
                writer.write(clothes.get(i).toFileString());
                writer.newLine();
            }
            System.out.println("Збережено: "+ clothes.size()+" об'єктів!");
        } catch (IOException e) {
            System.out.println("Помилка запису: " + e.getMessage());
        }
    }

    static void saveToJson (ArrayList<Clothes> clothes, String fileName){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray array = new JsonArray();
        JsonObject obj;
        for (int i=0; i<clothes.size(); i+=1){
            obj = gson.toJsonTree(clothes.get(i)).getAsJsonObject();
            obj.addProperty("type", clothes.get(i).getClass().getSimpleName());
            array.add(obj);
        }
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(array, writer);
            System.out.println("Збережено: "+ clothes.size()+" об'єктів!");
        }
        catch (IOException e) {
            System.out.println("Помилка запису: " + e.getMessage());
        }
    }

    /**
     * Шукає значення перерахування, назва для виведення якого ({@code toString()}) збігається з текстом.
     * @param text текст із файлу, наприклад {@code L} або {@code Чоловіча}
     * @param values масив значень перерахування для пошуку (результат {@code values()})
     * @param <E> тип перерахування
     * @return знайдене значення
     * @throws InvalidClothesDataException якщо жодне значення не збігається з текстом
     */
    static <E extends Enum<E>> E convertEnum (String text,  E[] values){
        for (int i=0; i<values.length; i+=1){
            if (Objects.equals(values[i].toString(), text)){
             return values[i];
            }
        }
        throw new InvalidClothesDataException("Невідоме значення: " + text);
    }

    /**
     * Зчитує об'єкти з файлу і створює їх за типом у першому полі рядка.
     * Порожні рядки й рядки, що починаються з {@code #}, пропускаються.
     * Рядок із невідомим типом, неправильною кількістю полів, нечисловим чи недопустимим
     * значенням пропускається з повідомленням, у якому вказано номер рядка й причину,
     * а решта файлу читається далі. Якщо файлу немає, повертається порожній список.
     * @param fileName назва файлу
     * @return список створених об'єктів (порожній, якщо файлу немає або в ньому немає коректних рядків)
     */
    static ArrayList<Clothes> loadFromFile (String fileName){
        ArrayList<Clothes> res=new ArrayList<>();
        int numberReadLine=0;
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line = "";
            while (true){
              line = reader.readLine();
              numberReadLine+=1;
              if (line == null) break;
              line=line.trim();
              if (line.isEmpty() || line.startsWith("#")){
                  continue;
              }
              try{
                  String[] itemFiled=line.split(";");
                  for (int i=0; i<itemFiled.length; i+=1){
                      itemFiled[i]=itemFiled[i].trim();
                  }
                  int expected = 0;
                  switch (itemFiled[0]){
                      case "Clothes":
                          expected = 8;
                          if (itemFiled.length!=8){
                              throw new InvalidClothesDataException("Неправильна кількість полів для " + itemFiled[0] + ": потрібно " + expected + ", а знайдено " + itemFiled.length);
                          }
                          Clothes item_cl = new Clothes(
                                  itemFiled[1],
                                  convertEnum(itemFiled[2], ClothingSize.values()),
                                  itemFiled[3],
                                  convertEnum(itemFiled[4], Gender.values()),
                                  Double.parseDouble(itemFiled[5]),
                                  itemFiled[6],
                                  Integer.parseInt(itemFiled[7])
                          );
                          res.add(item_cl);
                          break;
                      case "Jacket":
                          expected = 9;
                          if (itemFiled.length!=9){
                              throw new InvalidClothesDataException("Неправильна кількість полів для " + itemFiled[0] + ": потрібно " + expected + ", а знайдено " + itemFiled.length);
                          }
                          if(!Objects.equals(itemFiled[8], "true") && !Objects.equals(itemFiled[8], "false")){
                              throw new InvalidClothesDataException("Капюшон має бути true або false, а вказано: " + itemFiled[8]);
                          }
                          Jacket item_ja = new Jacket(
                                  itemFiled[1],
                                  convertEnum(itemFiled[2], ClothingSize.values()),
                                  itemFiled[3],
                                  convertEnum(itemFiled[4], Gender.values()),
                                  Double.parseDouble(itemFiled[5]),
                                  itemFiled[6],
                                  Integer.parseInt(itemFiled[7]),
                                  Boolean.parseBoolean(itemFiled[8])
                          );
                          res.add(item_ja);
                          break;
                      case "Pants":
                          expected = 9;
                          if (itemFiled.length!=9){
                              throw new InvalidClothesDataException("Неправильна кількість полів для " + itemFiled[0] + ": потрібно " + expected + ", а знайдено " + itemFiled.length);
                          }
                          Pants item_pan = new Pants(
                                  itemFiled[1],
                                  convertEnum(itemFiled[2], ClothingSize.values()),
                                  itemFiled[3],
                                  convertEnum(itemFiled[4], Gender.values()),
                                  Double.parseDouble(itemFiled[5]),
                                  itemFiled[6],
                                  Integer.parseInt(itemFiled[7]),
                                  Double.parseDouble(itemFiled[8])
                          );
                          res.add(item_pan);
                          break;
                      case "Shirts":
                          expected = 9;
                          if (itemFiled.length!=9){
                              throw new InvalidClothesDataException("Неправильна кількість полів для " + itemFiled[0] + ": потрібно " + expected + ", а знайдено " + itemFiled.length);
                          }
                          Shirts item_sh = new Shirts(
                                  itemFiled[1],
                                  convertEnum(itemFiled[2], ClothingSize.values()),
                                  itemFiled[3],
                                  convertEnum(itemFiled[4], Gender.values()),
                                  Double.parseDouble(itemFiled[5]),
                                  itemFiled[6],
                                  Integer.parseInt(itemFiled[7]),
                                  Integer.parseInt(itemFiled[8])
                          );
                          res.add(item_sh);
                          break;
                      case "Dress":
                          expected = 9;
                          if (itemFiled.length!=9){
                              throw new InvalidClothesDataException("Неправильна кількість полів для " + itemFiled[0] + ": потрібно " + expected + ", а знайдено " + itemFiled.length);
                          }
                          Dress item_dr = new Dress(
                                  itemFiled[1],
                                  convertEnum(itemFiled[2], ClothingSize.values()),
                                  itemFiled[3],
                                  convertEnum(itemFiled[4], Gender.values()),
                                  Double.parseDouble(itemFiled[5]),
                                  itemFiled[6],
                                  Integer.parseInt(itemFiled[7]),
                                  Integer.parseInt(itemFiled[8])
                          );
                          res.add(item_dr);
                          break;
                      default:
                          throw new InvalidClothesDataException("Невідомий тип: " + itemFiled[0] + " (допустимі: Clothes, Pants, Shirts, Jacket, Dress)");
                  }
              }
              catch (InvalidClothesDataException e){
                  System.out.println("Рядок "+numberReadLine+" пропущено: " + e.getMessage());
              }
              catch (NumberFormatException e){
                  System.out.println("Рядок " + numberReadLine + " пропущено: очікувалось число (" + e.getMessage() + ")");
              }
              }
        }
        catch (FileNotFoundException e){
            System.out.println("Файл " + fileName + " не знайдено, список порожній.");
        }
        catch (IOException e){
            System.out.println("Помилка: "+ e.getMessage());
        }
        return res;
    }

    static void checkFields(JsonObject obj, String[] fields){
        for (int i=0; i<fields.length; i+=1){
            if (!obj.has(fields[i])){
                throw new InvalidClothesDataException("Немає поля: "+ fields[i]);
            }
        }
    }

    static int readInt(JsonObject obj, String field){
        double number = obj.get(field).getAsDouble();
        int number_int = (int) number;
        if (number != (double) number_int){
            throw new InvalidClothesDataException ("Поле " + field + " має бути цілим числом, а вказано: " + obj.get(field));
        }
        return number_int;
    }

    static ArrayList<Clothes> loadFromJson (String fileName){
        ArrayList<Clothes> res=new ArrayList<>();
        try(FileReader reader = new FileReader(fileName)){
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (int i=0; i<array.size(); i+=1){
                try {
                    JsonObject obj = array.get(i).getAsJsonObject();
                    checkFields(obj, BASE_FIELDS);
                    switch (obj.get("type").getAsString()){
                        case "Clothes":
                            Clothes item_cl = new Clothes(
                                    obj.get("name").getAsString(),
                                    ClothingSize.valueOf(obj.get("size").getAsString()),
                                    obj.get("material").getAsString(),
                                    Gender.valueOf(obj.get("sexType").getAsString()),
                                    obj.get("price").getAsDouble(),
                                    obj.get("brand").getAsString(),
                                    readInt(obj, "quantity")
                            );
                            res.add(item_cl);
                            break;
                        case "Jacket":
                            checkFields(obj, new String[]{"hasHood"});
                            if(!obj.get("hasHood").getAsJsonPrimitive().isBoolean()){
                                throw new InvalidClothesDataException("Капюшон має бути true або false, а вказано: " + obj.get("hasHood"));
                            }
                            Jacket item_ja = new Jacket(
                                    obj.get("name").getAsString(),
                                    ClothingSize.valueOf(obj.get("size").getAsString()),
                                    obj.get("material").getAsString(),
                                    Gender.valueOf(obj.get("sexType").getAsString()),
                                    obj.get("price").getAsDouble(),
                                    obj.get("brand").getAsString(),
                                    readInt(obj, "quantity"),
                                    obj.get("hasHood").getAsBoolean()
                            );
                            res.add(item_ja);
                            break;
                        case "Pants":
                            checkFields(obj, new String[]{"length"});
                            Pants item_pan = new Pants(
                                    obj.get("name").getAsString(),
                                    ClothingSize.valueOf(obj.get("size").getAsString()),
                                    obj.get("material").getAsString(),
                                    Gender.valueOf(obj.get("sexType").getAsString()),
                                    obj.get("price").getAsDouble(),
                                    obj.get("brand").getAsString(),
                                    readInt(obj, "quantity"),
                                    obj.get("length").getAsDouble()
                            );
                            res.add(item_pan);
                            break;
                        case "Shirts":
                            checkFields(obj, new String[]{"sleeveLength"});
                            Shirts item_sh = new Shirts(
                                    obj.get("name").getAsString(),
                                    ClothingSize.valueOf(obj.get("size").getAsString()),
                                    obj.get("material").getAsString(),
                                    Gender.valueOf(obj.get("sexType").getAsString()),
                                    obj.get("price").getAsDouble(),
                                    obj.get("brand").getAsString(),
                                    readInt(obj, "quantity"),
                                    readInt(obj, "sleeveLength")
                            );
                            res.add(item_sh);
                            break;
                        case "Dress":
                            checkFields(obj, new String[]{"length"});
                            Dress item_dr = new Dress(
                                    obj.get("name").getAsString(),
                                    ClothingSize.valueOf(obj.get("size").getAsString()),
                                    obj.get("material").getAsString(),
                                    Gender.valueOf(obj.get("sexType").getAsString()),
                                    obj.get("price").getAsDouble(),
                                    obj.get("brand").getAsString(),
                                    readInt(obj, "quantity"),
                                    readInt(obj, "length")
                            );
                            res.add(item_dr);
                            break;
                        default:
                            throw new InvalidClothesDataException("Невідомий тип: " + obj.get("type").getAsString() + " (допустимі: Clothes, Pants, Shirts, Jacket, Dress)");
                    }
                }
                catch (IllegalArgumentException e){
                    System.out.println("Елемент "+(i+1)+" пропущено: "+ e.getMessage());
                }
                catch (JsonParseException | IllegalStateException | UnsupportedOperationException e){
                    System.out.println("Елемент " + (i + 1) + " пропущено: значення має неправильний тип (" + e.getMessage() + ")");
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Файл " + fileName + " не знайдено, список порожній.");
        }
        catch (IOException e){
            System.out.println("Помилка: "+ e.getMessage());
        }
        catch (IllegalStateException | JsonParseException e){
            System.out.println("Файл "+fileName+" не є коректним JSON-масивом, список порожній: "+ e.getMessage());
        }
        return res;
    }

    /**
     * Точка входу: показує меню та виконує вибрані дії, доки користувач не завершить роботу.
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║          ФОРМАТ ФАЙЛУ З ДАНИМИ           ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ 1. TXT  (input.txt)                      ║");
        System.out.println("║ 2. JSON (input.json)                     ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.print("Ваш вибір: ");
        int typeFile = readNumber(scanner, 1, 2);
        ArrayList<Clothes> clothes;
        if (typeFile ==1){
            clothes = loadFromFile(FILE_NAME);
        }
        else{
            clothes = loadFromJson(JSON_FILE_NAME);
        }
        System.out.println("Завантажено з файлу: "+clothes.size()+" об'єктів!");
        boolean run = true;
        while (true){
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║              МАГАЗИН ОДЯГУ               ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.println("║ 1. Створити новий об'єкт                 ║");
            System.out.println("║ 2. Вивести всі об'єкти                   ║");
            System.out.println("║ 3. Завершити роботу                      ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("Ваш вибір: ");
            int choice = readNumber(scanner, 1, 3);
            switch (choice){
                case 1:
                    System.out.println("1. Стандартний клас - Одяг");
                    System.out.println("2. Штани");
                    System.out.println("3. Футболка");
                    System.out.println("4. Куртка");
                    System.out.println("5. Сукня");
                    System.out.println("6. Вийти із створення об'єкта");
                    int type = readNumber(scanner, 1, 6);
                    if (type==6) break;
                    switch (type){
                        case 1:
                            Clothes item_cl = new Clothes();
                            basicField(scanner, item_cl, "Одяг", clothes.size()+1);
                            if (!isSave(scanner)) break;
                            clothes.add(item_cl);
                            break;
                        case 2:
                            Pants item_pan = new Pants();
                            basicField(scanner, item_pan, "Штани", clothes.size()+1);
                            readNumberField(scanner, "Довжина штанин (см): ", Double::parseDouble, item_pan::setLength);
                            if (!isSave(scanner)) break;
                            clothes.add(item_pan);
                            break;
                        case 3:
                            Shirts item_sh = new Shirts();
                            basicField(scanner, item_sh, "Футболка", clothes.size()+1);
                            readNumberField(scanner, "Довжина рукава (см): ", Integer::parseInt, item_sh::setSleeveLength);
                            if (!isSave(scanner)) break;
                            clothes.add(item_sh);
                            break;
                        case 4:
                            Jacket item_ja = new Jacket();
                            basicField(scanner, item_ja, "Куртка", clothes.size()+1);
                            readBooleanField(scanner, "З капюшоном?: ", item_ja::setHasHood);
                            if (!isSave(scanner)) break;
                            clothes.add(item_ja);
                            break;
                        case 5:
                            Dress item_dr = new Dress();
                            basicField(scanner, item_dr, "Сукня", clothes.size()+1);
                            readNumberField(scanner, "Довжина сукні (см): ", Integer::parseInt, item_dr::setLength);
                            if (!isSave(scanner)) break;
                            clothes.add(item_dr);
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
                    if (typeFile==1) saveToFile(clothes, FILE_NAME);
                    else saveToJson(clothes, JSON_FILE_NAME);
                    run = false;
                    break;
            }
            if (!run){
                break;
            }
        }
    }
}

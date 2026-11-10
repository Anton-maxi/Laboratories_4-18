package ua.sumdu.zboryk.lab7;

import java.util.ArrayList;

/**
 * Склад (магазин) одягу. Агрегує об'єкти {@link Clothes}: зберігає посилання на одяг,
 * який існує незалежно від складу й створюється окремо.
 */
public class ClothesStore {
    private final String name;
    private final ArrayList<Clothes> items = new ArrayList<>();

    /**
     * Створює порожній склад із заданою назвою.
     * @param name назва складу, не {@code null} і не порожня
     * @throws InvalidClothesDataException якщо назва {@code null} або порожня
     */
    public ClothesStore(String name){
        if (name == null || name.trim().isEmpty()){
            throw new InvalidClothesDataException("Назва не може бути порожньою");
        }
        this.name = name;
    }

    /**
     * Додає одяг на склад.
     * @param item одяг для додавання, не {@code null}
     * @throws InvalidClothesDataException якщо {@code item} дорівнює {@code null}
     */
    public void addItem(Clothes item) {
        if (item == null) {
            throw new InvalidClothesDataException("Не можна додати порожній об'єкт на склад");
        }
        items.add(item);
    }

    /**
     * Повертає одяг за індексом.
     * @param index індекс позиції, від 0 до {@code getSize() - 1}
     * @return одяг на вказаній позиції
     * @throws IllegalArgumentException якщо індекс виходить за межі списку
     */
    public Clothes getItem(int index){
        if (index<0 || index >= items.size()){
            throw new IllegalArgumentException("Помилка! Введіть індекс від 0 до "+(items.size()-1)+".");
        }
        return items.get(index);
    }

    /**
     * Повертає кількість позицій (видів одягу) на складі.
     * @return кількість позицій
     */
    public int getSize(){
        return items.size();
    }

    /**
     * Перевіряє, чи склад порожній.
     * @return {@code true}, якщо на складі немає жодної позиції
     */
    public boolean isEmpty(){
        return items.isEmpty();
    }

    /**
     * Рахує загальну кількість одиниць одягу на складі як суму кількостей усіх позицій.
     * @return загальна кількість одиниць одягу
     */
    public int getTotalQuantity(){
        int quantity=0;
        for (int i=0; i<items.size(); i+=1){
            quantity += getItem(i).getQuantity();
        }
        return quantity;
    }

    /**
     * Повертає текстове подання складу: назву й нумерований список позицій.
     * @return рядок із назвою складу та всіма позиціями
     */
    @Override
    public String toString() {
        String items_str="";
        for(int i=0; i<items.size(); i+=1){
            items_str+=(i + 1) + ". "+getItem(i).toString()+"\n";
        }
        return name+"\n"+items_str;
    }

}
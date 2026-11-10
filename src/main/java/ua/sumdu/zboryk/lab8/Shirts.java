package ua.sumdu.zboryk.lab8;

import java.util.Objects;

/**
 * Футболка: одяг із додатковою характеристикою довжини рукава.
 */
public class Shirts extends Clothes {
    private int sleeveLength;

    /**
     * Створює футболку зі значеннями за замовчуванням: базові поля як у {@link Clothes#Clothes()},
     * довжина рукава дорівнює 0.
     */
    public Shirts() {
        super();
        this.sleeveLength = 0;
    }

    /**
     * Створює футболку з перевіркою всіх значень.
     * @param name     назва, не {@code null} і не порожня
     * @param size     розмір, не {@code null}
     * @param material матеріал, не {@code null} і не порожній
     * @param sexType  стать, не {@code null}
     * @param price    ціна, більша за 0
     * @param brand    бренд, не {@code null} і не порожній
     * @param quantity кількість на складі, не менша за 0
     * @param sleeveLength довжина рукава в см, більша за 0
     * @throws InvalidClothesDataException якщо будь-яке значення некоректне
     */
    public Shirts(String name, ClothingSize size, String material, Gender sexType,
                  double price, String brand, int quantity, int sleeveLength) {
        super(name, size, material, sexType, price, brand, quantity);
        setSleeveLength(sleeveLength);
    }

    /**
     * Повертає довжину рукава.
     * @return довжина в см
     */
    public int getSleeveLength() {
        return sleeveLength;
    }

    /**
     * Задає довжину рукава.
     * @param sleeveLength довжина в см, більша за 0
     * @throws InvalidClothesDataException якщо довжина не більша за 0
     */
    public void setSleeveLength(int sleeveLength) {
        if (sleeveLength <= 0) {
            throw new InvalidClothesDataException("Довжина рукава має бути більшою за 0");
        }
        this.sleeveLength = sleeveLength;
    }

    /**
     * Повертає текстове подання футболки: усі поля одягу та довжину рукава.
     * @return рядок із типом, базовими полями й довжиною рукава
     */
    @Override
    public String toString() {
        return super.toString()+"\nsleeveLength = '"+sleeveLength+"'";
    }

    /**
     * Порівнює футболки за значеннями всіх полів, включно з довжиною рукава.
     * @param obj об'єкт для порівняння
     * @return {@code true}, якщо {@code obj} є футболкою з однаковими значеннями всіх полів
     */
    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)){
            Shirts clothes = (Shirts) obj;
            if (Objects.equals(sleeveLength, clothes.sleeveLength)) return true;
            return false;
        }
        return false;
    }
}

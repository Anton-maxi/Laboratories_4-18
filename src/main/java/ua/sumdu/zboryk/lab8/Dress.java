package ua.sumdu.zboryk.lab8;

import java.util.Objects;

/**
 * Сукня: одяг із додатковою характеристикою довжини.
 */
public class Dress extends Clothes {
    private int length;

    /**
     * Створює сукню зі значеннями за замовчуванням: базові поля як у {@link Clothes#Clothes()},
     * довжина дорівнює 0.
     */
    public Dress() {
        super();
        this.length = 0;
    }

    /**
     * Створює сукню з перевіркою всіх значень.
     * @param name     назва, не {@code null} і не порожня
     * @param size     розмір, не {@code null}
     * @param material матеріал, не {@code null} і не порожній
     * @param sexType  стать, не {@code null}
     * @param price    ціна, більша за 0
     * @param brand    бренд, не {@code null} і не порожній
     * @param quantity кількість на складі, не менша за 0
     * @param length   довжина сукні в см, більша за 0
     * @throws InvalidClothesDataException якщо будь-яке значення некоректне
     */
    public Dress(String name, ClothingSize size, String material, Gender sexType,
                 double price, String brand, int quantity, int length) {
        super(name, size, material, sexType, price, brand, quantity);
        setLength(length);
    }


    /**
     * Повертає довжину сукні.
     * @return довжина в см
     */
    public int getLength() {
        return length;
    }

    /**
     * Задає довжину сукні.
     * @param length довжина в см, більша за 0
     * @throws InvalidClothesDataException якщо довжина не більша за 0
     */
    public void setLength(int length) {
        if (length <= 0) {
            throw new InvalidClothesDataException("Довжина сукні має бути більшою за 0");
        }
        this.length = length;
    }

    /**
     * Повертає текстове подання сукні: усі поля одягу та довжину.
     * @return рядок із типом, базовими полями й довжиною
     */
    @Override
    public String toString() {
        return super.toString()+"\nlength = '"+length+"'";
    }

    /**
     * Порівнює сукні за значеннями всіх полів, включно з довжиною.
     * @param obj об'єкт для порівняння
     * @return {@code true}, якщо {@code obj} є сукнею з однаковими значеннями всіх полів
     */
    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)){
            Dress clothes = (Dress) obj;
            if (Objects.equals(length, clothes.length)) return true;
            return false;
        }
        return false;
    }
}

package ua.sumdu.zboryk.lab7;

/**
 * Штани: одяг із додатковою характеристикою довжини штанин.
 */
public class Pants extends Clothes {
    private double length;

    /**
     * Створює штани зі значеннями за замовчуванням: базові поля як у {@link Clothes#Clothes()},
     * довжина штанин дорівнює 0.
     */
    public Pants() {
        super();
        this.length = 0;
    }

    /**
     * Створює штани з перевіркою всіх значень.
     * @param name     назва, не {@code null} і не порожня
     * @param size     розмір, не {@code null}
     * @param material матеріал, не {@code null} і не порожній
     * @param sexType  стать, не {@code null}
     * @param price    ціна, більша за 0
     * @param brand    бренд, не {@code null} і не порожній
     * @param quantity кількість на складі, не менша за 0
     * @param length   довжина штанин у см, більша за 0
     * @throws InvalidClothesDataException якщо будь-яке значення некоректне
     */
    public Pants(String name, ClothingSize size, String material, Gender sexType,
                 double price, String brand, int quantity, double length) {
        super(name, size, material, sexType, price, brand, quantity);
        setLength(length);
    }

    /**
     * Повертає довжину штанин.
     * @return довжина в см
     */
    public double getLength() {
        return length;
    }

    /**
     * Задає довжину штанин.
     * @param length довжина в см, більша за 0
     * @throws InvalidClothesDataException якщо довжина не більша за 0 або не є числом
     */
    public void setLength(double length) {
        if (length <= 0 || Double.isNaN(length)) {
            throw new InvalidClothesDataException("Довжина штанин має бути більшою за 0");
        }
        this.length = length;
    }

    /**
     * Повертає текстове подання штанів: усі поля одягу та довжину штанин.
     * @return рядок із типом, базовими полями й довжиною штанин
     */
    @Override
    public String toString() {
        return super.toString()+"\nlength = '"+length+"'";
    }

    /**
     * Порівнює штани за значеннями всіх полів, включно з довжиною штанин.
     * @param obj об'єкт для порівняння
     * @return {@code true}, якщо {@code obj} є штанами з однаковими значеннями всіх полів
     */
    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)){
            Pants clothes = (Pants) obj;
            if (Double.compare(length, clothes.length)== 0) return true;
            return false;
        }
        return false;
    }
}
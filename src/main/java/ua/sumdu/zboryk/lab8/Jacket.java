package ua.sumdu.zboryk.lab8;

/**
 * Куртка: одяг із додатковою характеристикою наявності капюшона.
 */
public class Jacket extends Clothes {
    private boolean hasHood;

    /**
     * Створює куртку зі значеннями за замовчуванням: базові поля як у {@link Clothes#Clothes()},
     * капюшона немає.
     */
    public Jacket() {
        super();
        this.hasHood=false;
    }

    /**
     * Створює куртку з перевіркою всіх базових значень.
     * @param name     назва, не {@code null} і не порожня
     * @param size     розмір, не {@code null}
     * @param material матеріал, не {@code null} і не порожній
     * @param sexType  стать, не {@code null}
     * @param price    ціна, більша за 0
     * @param brand    бренд, не {@code null} і не порожній
     * @param quantity кількість на складі, не менша за 0
     * @param hasHood  {@code true}, якщо в куртки є капюшон
     * @throws InvalidClothesDataException якщо будь-яке базове значення некоректне
     */
    public Jacket(String name, ClothingSize size, String material, Gender sexType,
                  double price, String brand, int quantity, boolean hasHood) {
        super(name, size, material, sexType, price, brand, quantity);
        this.hasHood=hasHood;
    }

    /**
     * Повертає наявність капюшона.
     * @return {@code true}, якщо капюшон є
     */
    public boolean isHasHood() {
        return hasHood;
    }

    /**
     * Задає наявність капюшона.
     * @param hasHood {@code true}, якщо капюшон є
     */
    public void setHasHood(boolean hasHood) {
        this.hasHood = hasHood;
    }

    /**
     * Повертає текстове подання куртки: усі поля одягу та наявність капюшона.
     * @return рядок із типом, базовими полями й наявністю капюшона
     */
    @Override
    public String toString() {
        return super.toString()+"\nhasHood = '"+hasHood+"'";
    }

    /**
     * Порівнює куртки за значеннями всіх полів, включно з наявністю капюшона.
     * @param obj об'єкт для порівняння
     * @return {@code true}, якщо {@code obj} є курткою з однаковими значеннями всіх полів
     */
    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)){
            Jacket clothes = (Jacket) obj;
            if (hasHood ==clothes.hasHood) return true;
            return false;
        }
        return false;
    }
}
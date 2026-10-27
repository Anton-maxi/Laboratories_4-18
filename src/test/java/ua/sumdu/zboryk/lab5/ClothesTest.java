package ua.sumdu.zboryk.lab5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Автотести перевірки коректності даних класу {@link Clothes}: кидання
 * {@link InvalidClothesDataException} у сетерах і конструкторі та прийняття коректних значень.
 */
class ClothesTest {

    /**
     * Створює коректний об'єкт одягу для тестів сетерів.
     * @return одяг із правильними значеннями всіх полів
     */
    private Clothes createValidItem() {
        return new Clothes("Куртка", "M", "Бавовна", "Чоловіча", 499.99, "Nike", 3);
    }

    /** Сетер ціни відхиляє від'ємне значення. */
    @Test
    void shouldThrowExceptionWhenNegativePriceInSetter() {
        Clothes testItem = createValidItem();
        assertThrows(InvalidClothesDataException.class, () -> {
            testItem.setPrice(-2);
        });
    }

    /** Сетер ціни відхиляє значення 0, бо ціна має бути більшою за 0. */
    @Test
    void shouldThrowExceptionWhenZeroPriceInSetter() {
        Clothes testItem = createValidItem();
        assertThrows(InvalidClothesDataException.class, () -> {
            testItem.setPrice(0);
        });
    }

    /** Сетер кількості відхиляє від'ємне значення. */
    @Test
    void shouldThrowExceptionWhenNegativeQuantityInSetter() {
        Clothes testItem = createValidItem();
        assertThrows(InvalidClothesDataException.class, () -> {
            testItem.setQuantity(-189);
        });
    }

    /** Сетер назви відхиляє рядок, що складається лише з пробілів. */
    @Test
    void shouldThrowExceptionWhenBlankNameInSetter() {
        Clothes testItem = createValidItem();
        assertThrows(InvalidClothesDataException.class, () -> {
            testItem.setName("  ");
        });
    }

    /** Сетер бренду відхиляє значення {@code null}. */
    @Test
    void shouldThrowExceptionWhenNullBrandInSetter() {
        Clothes testItem = createValidItem();
        assertThrows(InvalidClothesDataException.class, () -> {
            testItem.setBrand(null);
        });
    }

    /** Конструктор кидає виняток, якщо назва складається лише з пробілів. */
    @Test
    void shouldThrowExceptionWhenBlankNameInConstructor() {
        assertThrows(InvalidClothesDataException.class, () -> {
            new Clothes("  ", "M", "Бавовна", "Чоловіча", 499.99, "Nike", 3);
        });
    }

    /** Конструктор кидає виняток, якщо ціна від'ємна. */
    @Test
    void shouldThrowExceptionWhenNegativePriceInConstructor() {
        assertThrows(InvalidClothesDataException.class, () -> {
            new Clothes("Футболка", "M", "Бавовна", "Чоловіча", -1, "Nike", 3);
        });
    }

    /** Конструктор приймає коректні дані без винятку. */
    @Test
    void shouldNotThrowExceptionWhenValidConstructorData() {
        assertDoesNotThrow(() -> {
            Clothes testItem = new Clothes("Куртка", "M", "Бавовна", "Чоловіча", 499.99, "Nike", 3);
        });
    }

    /** Сетер кількості приймає значення 0 без винятку, бо нуль на складі допустимий. */
    @Test
    void shouldNotThrowExceptionWhenZeroQuantityInSetter() {
        Clothes testItem = createValidItem();
        assertDoesNotThrow(() -> {
            testItem.setQuantity(0);
        });
    }
}
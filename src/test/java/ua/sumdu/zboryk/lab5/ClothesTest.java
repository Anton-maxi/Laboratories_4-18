package ua.sumdu.zboryk.lab5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    private Clothes createValidItem() {
        return new Clothes("Куртка", "M", "Бавовна", "Чоловіча", 499.99, "Nike", 3);
    }

    @Test
    void shouldThrowExceptionWhenNegativePriceInSetter() {
        Clothes testItem = createValidItem();
        assertThrows(InvalidClothesDataException.class, () -> {
            testItem.setPrice(-2);
        });
    }

    @Test
    void shouldThrowExceptionWhenZeroPriceInSetter() {
        Clothes testItem = createValidItem();
        assertThrows(InvalidClothesDataException.class, () -> {
            testItem.setPrice(0);
        });
    }

    @Test
    void shouldThrowExceptionWhenNegativeQuantityInSetter() {
        Clothes testItem = createValidItem();
        assertThrows(InvalidClothesDataException.class, () -> {
            testItem.setQuantity(-189);
        });
    }

    @Test
    void shouldThrowExceptionWhenBlankNameInSetter() {
        Clothes testItem = createValidItem();
        assertThrows(InvalidClothesDataException.class, () -> {
            testItem.setName("  ");
        });
    }

    @Test
    void shouldThrowExceptionWhenNullBrandInSetter() {
        Clothes testItem = createValidItem();
        assertThrows(InvalidClothesDataException.class, () -> {
            testItem.setBrand(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenBlankNameInConstructor() {
        assertThrows(InvalidClothesDataException.class, () -> {
            new Clothes("  ", "M", "Бавовна", "Чоловіча", 499.99, "Nike", 3);
        });
    }

    @Test
    void shouldThrowExceptionWhenNegativePriceInConstructor() {
        assertThrows(InvalidClothesDataException.class, () -> {
            new Clothes("Футболка", "M", "Бавовна", "Чоловіча", -1, "Nike", 3);
        });
    }

    @Test
    void shouldNotThrowExceptionWhenValidConstructorData() {
        assertDoesNotThrow(() -> {
            Clothes testItem = new Clothes("Куртка", "M", "Бавовна", "Чоловіча", 499.99, "Nike", 3);
        });
    }

    @Test
    void shouldNotThrowExceptionWhenZeroQuantityInSetter() {
        Clothes testItem = createValidItem();
        assertDoesNotThrow(() -> {
            testItem.setQuantity(0);
        });
    }
}
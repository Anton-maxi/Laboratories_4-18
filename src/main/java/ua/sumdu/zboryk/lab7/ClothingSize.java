package ua.sumdu.zboryk.lab7;

/**
 * Розмір одягу.
 */
public enum ClothingSize {
    XS("XS"),
    S("S"),
    M("M"),
    L("L"),
    XL("XL"),
    XXL("XXL");
    private final String displayName;

    /**
     * Створює значення перерахування з назвою для виведення.
     * @param displayName назва розміру для виведення
     */
    ClothingSize(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Повертає назву розміру для виведення користувачу.
     * @return назва значення
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Повертає назву розміру для виведення користувачу.
     * @return displayName
     */
    @Override
    public String toString() {
        return displayName;
    }
}

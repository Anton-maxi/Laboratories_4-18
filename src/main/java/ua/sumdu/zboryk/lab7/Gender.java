package ua.sumdu.zboryk.lab7;

/**
 * Стать, для якої призначений одяг.
 */
public enum Gender {
    MALE("Чоловіча"),
    FEMALE("Жіноча"),
    UNISEX("Унісекс");

    private final String displayName;

    /**
     * Створює значення перерахування з назвою для виведення.
     * @param displayName назва українською
     */
    Gender(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Повертає назву українською для виведення користувачу.
     * @return назва значення
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Повертає назву українською для виведення користувачу.
     * @return displayName
     */
    @Override
    public String toString() {
        return displayName;
    }
}
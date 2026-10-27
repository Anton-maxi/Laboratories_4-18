package ua.sumdu.zboryk.lab5;

import java.util.Objects;

/**
 * Одяг із назвою, розміром, матеріалом, статтю, ціною, брендом і кількістю на складі.
 * Усі значення перевіряються в сетерах, а конструктор із параметрами використовує
 * ті самі сетери, тому правила валідації задані в одному місці.
 */
public class Clothes {
    private String name, size, material, sexType, brand;
    private double price;
    private int quantity;

    /**
     * Створює одяг із перевіркою всіх значень.
     * @param name     назва, не {@code null} і не порожня
     * @param size     розмір, не {@code null} і не порожній
     * @param material матеріал, не {@code null} і не порожній
     * @param sexType  стать, не {@code null} і не порожня
     * @param price    ціна, більша за 0
     * @param brand    бренд, не {@code null} і не порожній
     * @param quantity кількість на складі, не менша за 0
     * @throws InvalidClothesDataException якщо будь-яке значення некоректне
     */
    public Clothes(String name, String size, String material, String sexType, double price, String brand, int quantity){
        setMaterial(material);
        setBrand(brand);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        setSexType(sexType);
        setSize(size);
    }

    /**
     * Створює одяг зі значеннями за замовчуванням: текстові поля мають значення
     * {@code "none"}, а ціна й кількість дорівнюють 0.
     */
    public Clothes(){
        this.material = "none";
        this.name = "none";
        this.price = 0;
        this.sexType = "none";
        this.size = "none";
        this.brand = "none";
        this.quantity = 0;
    }

    /**
     * Повертає ціну.
     * @return ціна
     */
    public double getPrice() {
        return price;
    }

    /**
     * Повертає матеріал.
     * @return матеріал
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Повертає назву.
     * @return назва
     */
    public String getName() {
        return name;
    }

    /**
     * Повертає стать, для якої призначений одяг.
     * @return стать
     */
    public String getSexType() {
        return sexType;
    }

    /**
     * Повертає розмір.
     * @return розмір
     */
    public String getSize() {
        return size;
    }

    /**
     * Повертає кількість на складі.
     * @return кількість
     */
    public int getQuantity() { return quantity; }

    /**
     * Повертає бренд.
     * @return бренд
     */
    public String getBrand() { return brand; }

    /**
     * Задає матеріал.
     * @param material матеріал, не {@code null} і не порожній
     * @throws InvalidClothesDataException якщо матеріал {@code null} або порожній
     */
    public void setMaterial(String material) {
        if(material == null || material.trim().isEmpty()){
            throw new InvalidClothesDataException("Матеріал не може бути порожнім");
        }
        this.material = material;
    }

    /**
     * Задає назву.
     * @param name назва, не {@code null} і не порожня
     * @throws InvalidClothesDataException якщо назва {@code null} або порожня
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new InvalidClothesDataException("Назва не може бути порожньою");
        }
        this.name = name;
    }

    /**
     * Задає ціну.
     * @param price ціна, більша за 0
     * @throws InvalidClothesDataException якщо ціна не більша за 0 або не є числом
     */
    public void setPrice(double price) {
        if (price<=0 || Double.isNaN(price)){
            throw new InvalidClothesDataException("Ціна має бути більшою за 0");
        }
        this.price = price;
    }

    /**
     * Задає стать.
     * @param sexType стать, не {@code null} і не порожня
     * @throws InvalidClothesDataException якщо стать {@code null} або порожня
     */
    public void setSexType(String sexType) {
        if(sexType == null || sexType.trim().isEmpty()){
            throw new InvalidClothesDataException("Стать не може бути порожньою");
        }
        this.sexType = sexType;
    }

    /**
     * Задає розмір.
     * @param size розмір, не {@code null} і не порожній
     * @throws InvalidClothesDataException якщо розмір {@code null} або порожній
     */
    public void setSize(String size) {
        if(size == null || size.trim().isEmpty()){
            throw new InvalidClothesDataException("Розмір не може бути порожнім");
        }
        this.size = size;
    }

    /**
     * Задає бренд.
     * @param brand бренд, не {@code null} і не порожній
     * @throws InvalidClothesDataException якщо бренд {@code null} або порожній
     */
    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()){
            throw new InvalidClothesDataException("Бренд не може бути порожнім");
        }
        this.brand = brand;
    }

    /**
     * Задає кількість на складі.
     * @param quantity кількість, не менша за 0
     * @throws InvalidClothesDataException якщо кількість від'ємна
     */
    public void setQuantity(int quantity) {
        if (quantity < 0){
            throw new InvalidClothesDataException("Кількість не може бути від'ємною");
        }
        this.quantity = quantity;
    }

    /**
     * Повертає текстове подання об'єкта з усіма полями.
     * @return рядок із назвою, матеріалом, статтю, розміром, ціною, брендом і кількістю
     */
    @Override
    public String toString() {
        return "Clothes{name='"+name+"', material='"+material+"', sexType='"+sexType+"', size='"+size+"', price='"+price+"', brand='"+brand+"', quantity='"+quantity+"'}";
    }

    /**
     * Порівнює одяг за значеннями всіх полів.
     * @param obj об'єкт для порівняння
     * @return {@code true}, якщо {@code obj} є одягом з однаковими значеннями всіх полів
     */
    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        Clothes clothes = (Clothes) obj;
        if (Objects.equals(name, clothes.name) && Objects.equals(material, clothes.material) && Objects.equals(sexType, clothes.sexType) && Objects.equals(size, clothes.size) && Double.compare(price, clothes.price) == 0 && Objects.equals(brand, clothes.brand) && quantity == clothes.quantity) return true;
        return false;
    }
}
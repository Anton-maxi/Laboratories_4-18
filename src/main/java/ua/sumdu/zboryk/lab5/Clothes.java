package ua.sumdu.zboryk.lab5;

import java.util.Objects;

public class Clothes {
    private String name, size, material, sexType, brand;
    private double price;
    private int quantity;

    public Clothes(String name, String size, String material, String sexType, double price, String brand, int quantity){
        setMaterial(material);
        setBrand(brand);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        setSexType(sexType);
        setSize(size);
    }

    public Clothes(){
        this.material = "none";
        this.name = "none";
        this.price = 0;
        this.sexType = "none";
        this.size = "none";
        this.brand = "none";
        this.quantity = 0;
    }

    public double getPrice() {
        return price;
    }

    public String getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public String getSexType() {
        return sexType;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() { return quantity; }

    public String getBrand() { return brand; }

    public void setMaterial(String material) {
        if(material == null || material.trim().isEmpty()){
            throw new InvalidClothesDataException("Матеріал не може бути порожнім");
        }
        this.material = material;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new InvalidClothesDataException("Назва не може бути порожньою");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        if (price<=0 || Double.isNaN(price)){
            throw new InvalidClothesDataException("Ціна має бути більшою за 0");
        }
        this.price = price;
    }

    public void setSexType(String sexType) {
        if(sexType == null || sexType.trim().isEmpty()){
            throw new InvalidClothesDataException("Стать не може бути порожньою");
        }
        this.sexType = sexType;
    }

    public void setSize(String size) {
        if(size == null || size.trim().isEmpty()){
            throw new InvalidClothesDataException("Розмір не може бути порожнім");
        }
        this.size = size;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()){
            throw new InvalidClothesDataException("Бренд не може бути порожнім");
        }
        this.brand = brand;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0){
            throw new InvalidClothesDataException("Кількість не може бути від'ємною");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Clothes{name='"+name+"', material='"+material+"', sexType='"+sexType+"', size='"+size+"', price='"+price+"', brand='"+brand+"', quantity='"+quantity+"'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        Clothes clothes = (Clothes) obj;
        if (Objects.equals(name, clothes.name) && Objects.equals(material, clothes.material) && Objects.equals(sexType, clothes.sexType) && Objects.equals(size, clothes.size) && Double.compare(price, clothes.price) == 0 && Objects.equals(brand, clothes.brand) && quantity == clothes.quantity) return true;
        return false;
    }
}

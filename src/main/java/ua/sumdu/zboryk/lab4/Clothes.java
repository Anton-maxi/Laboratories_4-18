package ua.sumdu.zboryk.lab4;

import java.util.Objects;

public class Clothes {
    private String name, size, material, sexType;
    private double price;

    public Clothes(String name, String size, String material, String sexType, double price){
        this.material = material;
        this.name = name;
        this.price = price;
        this.sexType = sexType;
        this.size = size;
    }

    public Clothes(){
        this.material = "none";
        this.name = "none";
        this.price = 0;
        this.sexType = "none";
        this.size = "none";
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

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSexType(String sexType) {
        this.sexType = sexType;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Clothes{name='"+name+"', material='"+material+"', sexType='"+sexType+"', size='"+size+"', price='"+price+"'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        Clothes clothes = (Clothes) obj;
        if (Objects.equals(name, clothes.name) && Objects.equals(material, clothes.material) && Objects.equals(sexType, clothes.sexType) && Objects.equals(size, clothes.size) && Double.compare(price, clothes.price) == 0) return true;
        return false;
    }
}

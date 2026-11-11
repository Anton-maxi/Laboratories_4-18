package ua.sumdu.zboryk.lab8;

import java.util.Objects;

public class Dress extends Clothes {
    private int length;

    public Dress() {
        super();
        this.length = 0;
    }

    public Dress(String name, ClothingSize size, String material, Gender sexType,
                 double price, String brand, int quantity, int length) {
        super(name, size, material, sexType, price, brand, quantity);
        setLength(length);
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        if (length <= 0) {
            throw new InvalidClothesDataException("Довжина сукні має бути більшою за 0");
        }
        this.length = length;
    }

    @Override
    public String toString() {
        return super.toString()+"\nlength = '"+length+"'";
    }

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

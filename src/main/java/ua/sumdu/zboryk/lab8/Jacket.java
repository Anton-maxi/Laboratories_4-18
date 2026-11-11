package ua.sumdu.zboryk.lab8;

public class Jacket extends Clothes {
    private boolean hasHood;

    public Jacket() {
        super();
        this.hasHood=false;
    }

    public Jacket(String name, ClothingSize size, String material, Gender sexType,
                  double price, String brand, int quantity, boolean hasHood) {
        super(name, size, material, sexType, price, brand, quantity);
        this.hasHood=hasHood;
    }

    public boolean isHasHood() {
        return hasHood;
    }

    public void setHasHood(boolean hasHood) {
        this.hasHood = hasHood;
    }

    @Override
    public String toString() {
        return super.toString()+"\nhasHood = '"+hasHood+"'";
    }

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
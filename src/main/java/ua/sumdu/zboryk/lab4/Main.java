package ua.sumdu.zboryk.lab4;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть кількість одягу: ");
        int count;
        while (true){
            try {
                count = scanner.nextInt();
                if(count<=0) throw new InputMismatchException();
                break;
            }
            catch (InputMismatchException e){
                System.out.print("Помилка! Введіть кількість одягу (ціле додатне число): ");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        Clothes[] clothes = new Clothes[count];
        for (int i = 0; i < count; i++){
            System.out.print("Введіть властивості для Одягу "+ (i+1)+ ":\n");
            System.out.print("Назва: ");
            String name, size, material, sexType;
            double price;
            name = scanner.nextLine();
            System.out.print("Матеріал: ");
            material = scanner.nextLine();
            System.out.print("Розмір: ");
            size = scanner.nextLine();
            System.out.print("Стать: ");
            sexType = scanner.nextLine();
            System.out.print("Ціна (через кому): ");
            while (true){
                try {
                    price = scanner.nextDouble();
                    if(price<=0) throw new InputMismatchException();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.print("Помилка! Введіть ціну одягу " + (i+1) + " (додатне число): ");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();
            clothes[i]=new Clothes(name, size, material, sexType, price);
        }
        for (int i=0; i < count; i++){
            System.out.println((i + 1) + ". " + clothes[i]);
        }
        Clothes copy = new Clothes(clothes[0].getName(), clothes[0].getSize(), clothes[0].getMaterial(), clothes[0].getSexType(), clothes[0].getPrice());
        System.out.println("Чи однакові об'єкти copy та clothes[0]: "+ clothes[0].equals(copy));
        System.out.println("Чи copy == clothes[0]: "+ (copy == clothes[0]));
        if (count > 1) {
            System.out.println("Перший одяг equals другий: " + clothes[0].equals(clothes[1]));
        }
    }
}

package ua.sumdu.zboryk.lab5;

/**
 * Виняток, який сигналізує про некоректні дані об'єкта {@link Clothes}.
 * Є непрехованим (unchecked) нащадком {@link IllegalArgumentException}, тому
 * його не потрібно оголошувати через {@code throws}.
 */
public class InvalidClothesDataException extends IllegalArgumentException {

    /**
     * Створює виняток із повідомленням про причину помилки.
     * @param message текст повідомлення, зрозумілий користувачу
     */
    public InvalidClothesDataException(String message){
        super(message);
    }
}
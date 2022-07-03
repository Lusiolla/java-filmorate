package ru.yandex.practicum.filmorate.exeption;

public class MpaNotFoundException extends RuntimeException{
    public MpaNotFoundException() {
    }

    public MpaNotFoundException(String message) {
        super(message);
    }
}

package ru.yandex.practicum.filmorate.exeption;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException() {
    }

    public GenreNotFoundException(String message) {
        super(message);
    }
}

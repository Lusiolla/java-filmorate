package ru.yandex.practicum.filmorate.exeption;

public class UserFriendNotFoundException extends RuntimeException {
    public UserFriendNotFoundException() {
    }

    public UserFriendNotFoundException (final String message) {
        super(message);
    }
}

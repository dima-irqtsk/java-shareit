package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {
    User add(User user);

    User update(User user);

    User getById(long userId);

    List<User> getAll();

    void deleteById(long userId);

    void checkUserExists(long userId);
}
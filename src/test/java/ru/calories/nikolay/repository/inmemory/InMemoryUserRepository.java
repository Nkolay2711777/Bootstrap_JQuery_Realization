package ru.calories.nikolay.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.calories.nikolay.UserTestData;
import ru.calories.nikolay.model.User;
import ru.calories.nikolay.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    public void init() {
        map.clear();
        put(UserTestData.user);
        put(UserTestData.admin);
        put(UserTestData.guest);
        counter.getAndSet(UserTestData.GUEST_ID + 1);
    }

    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .toList();
    }

    @Override
    public User getByEmail(String email) {
        Objects.requireNonNull(email, "email must not be null");
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
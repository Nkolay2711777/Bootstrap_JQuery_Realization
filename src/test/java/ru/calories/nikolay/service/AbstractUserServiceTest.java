package ru.calories.nikolay.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.calories.nikolay.UserTestData;
import ru.calories.nikolay.model.Role;
import ru.calories.nikolay.model.User;
import ru.calories.nikolay.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    public void create() {
        User created = service.create(UserTestData.getNew());
        int newId = created.id();
        User newUser = UserTestData.getNew();
        newUser.setId(newId);
        UserTestData.USER_MATCHER.assertMatch(created, newUser);
        UserTestData.USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", 2000, Role.USER)));
    }

    @Test
    void delete() {
        service.delete(UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(UserTestData.USER_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(UserTestData.NOT_FOUND));
    }

    @Test
    void get() {
        User user = service.get(UserTestData.ADMIN_ID);
        UserTestData.USER_MATCHER.assertMatch(user, UserTestData.admin);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(UserTestData.NOT_FOUND));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        UserTestData.USER_MATCHER.assertMatch(user, UserTestData.admin);
    }

    @Test
    void update() {
        User updated = UserTestData.getUpdated();
        service.update(updated);
        UserTestData.USER_MATCHER.assertMatch(service.get(UserTestData.USER_ID), UserTestData.getUpdated());
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        UserTestData.USER_MATCHER.assertMatch(all, UserTestData.admin, UserTestData.guest, UserTestData.user);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "  ", "mail@yandex.ru", "password", 2000, Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "  ", "password", 2000, Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru", "password", 9, true, new Date(), Set.of())));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru", "password", 10001, true, new Date(), Set.of())));
    }

    @Test
    void enable() {
        service.enable(UserTestData.USER_ID, false);
        assertFalse(service.get(UserTestData.USER_ID).isEnabled());
        service.enable(UserTestData.USER_ID, true);
        assertTrue(service.get(UserTestData.USER_ID).isEnabled());
    }
}
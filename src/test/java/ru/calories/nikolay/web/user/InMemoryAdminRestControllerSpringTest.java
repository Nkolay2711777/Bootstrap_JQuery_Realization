package ru.calories.nikolay.web.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.calories.nikolay.UserTestData;
import ru.calories.nikolay.repository.inmemory.InMemoryUserRepository;
import ru.calories.nikolay.util.exception.NotFoundException;

@SpringJUnitConfig(locations = {"classpath:spring/inmemory.xml"})
class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @BeforeEach
    public void setUp() {
        repository.init();
    }

    @Test
    void delete() {
        controller.delete(UserTestData.USER_ID);
        Assertions.assertNull(repository.get(UserTestData.USER_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> controller.delete(UserTestData.NOT_FOUND));
    }
}
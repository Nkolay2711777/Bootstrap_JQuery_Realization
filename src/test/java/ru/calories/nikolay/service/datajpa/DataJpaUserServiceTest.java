package ru.calories.nikolay.service.datajpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.calories.nikolay.UserTestData;
import ru.calories.nikolay.model.User;
import ru.calories.nikolay.service.AbstractUserServiceTest;
import ru.calories.nikolay.util.exception.NotFoundException;

import static ru.calories.nikolay.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    void getWithMeals() {
        User actual = service.getWithMeals(UserTestData.ADMIN_ID);
        UserTestData.USER_WITH_MEALS_MATCHER.assertMatch(actual, UserTestData.admin);
    }

    @Test
    void getWithMealsNotFound() {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithMeals(UserTestData.NOT_FOUND));
    }
}
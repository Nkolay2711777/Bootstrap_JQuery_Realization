package ru.calories.nikolay.service.datajpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.calories.nikolay.MealTestData;
import ru.calories.nikolay.UserTestData;
import ru.calories.nikolay.model.Meal;
import ru.calories.nikolay.util.exception.NotFoundException;
import ru.calories.nikolay.service.AbstractMealServiceTest;

import static ru.calories.nikolay.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    void getWithUser() {
        Meal adminMeal = service.getWithUser(MealTestData.ADMIN_MEAL_ID, UserTestData.ADMIN_ID);
        MealTestData.MEAL_MATCHER.assertMatch(adminMeal, MealTestData.adminMeal1);
        UserTestData.USER_MATCHER.assertMatch(adminMeal.getUser(), UserTestData.admin);
    }

    @Test
    void getWithUserNotFound() {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithUser(1, UserTestData.ADMIN_ID));
    }
}

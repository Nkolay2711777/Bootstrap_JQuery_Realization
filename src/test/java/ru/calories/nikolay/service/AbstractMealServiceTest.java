package ru.calories.nikolay.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.calories.nikolay.MealTestData;
import ru.calories.nikolay.UserTestData;
import ru.calories.nikolay.model.Meal;
import ru.calories.nikolay.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractMealServiceTest extends AbstractServiceTest {

    @Autowired
    protected MealService service;

    @Test
    void delete() {
        service.delete(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.MEAL1_ID, UserTestData.ADMIN_ID));
    }

    @Test
    void create() {
        Meal created = service.create(MealTestData.getNew(), UserTestData.USER_ID);
        int newId = created.id();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MealTestData.MEAL_MATCHER.assertMatch(created, newMeal);
        MealTestData.MEAL_MATCHER.assertMatch(service.get(newId, UserTestData.USER_ID), newMeal);
    }

    @Test
    void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, MealTestData.meal1.getDateTime(), "duplicate", 100), UserTestData.USER_ID));
    }

    @Test
    void get() {
        Meal actual = service.get(MealTestData.ADMIN_MEAL_ID, UserTestData.ADMIN_ID);
        MealTestData.MEAL_MATCHER.assertMatch(actual, MealTestData.adminMeal1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.MEAL1_ID, UserTestData.ADMIN_ID));
    }

    @Test
    void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, UserTestData.USER_ID);
        MealTestData.MEAL_MATCHER.assertMatch(service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID), MealTestData.getUpdated());
    }

    @Test
    void updateNotOwn() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(MealTestData.getUpdated(), UserTestData.ADMIN_ID));
        Assertions.assertEquals("Not found entity with id=" + MealTestData.MEAL1_ID, exception.getMessage());
        MealTestData.MEAL_MATCHER.assertMatch(service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID), MealTestData.meal1);
    }

    @Test
    void getAll() {
        MealTestData.MEAL_MATCHER.assertMatch(service.getAll(UserTestData.USER_ID), MealTestData.meals);
    }

    @Test
    void getBetweenInclusive() {
        MealTestData.MEAL_MATCHER.assertMatch(service.getBetweenInclusive(
                        LocalDate.of(2020, Month.JANUARY, 30),
                        LocalDate.of(2020, Month.JANUARY, 30), UserTestData.USER_ID),
                MealTestData.meal3, MealTestData.meal2, MealTestData.meal1);
    }

    @Test
    void getBetweenWithNullDates() {
        MealTestData.MEAL_MATCHER.assertMatch(service.getBetweenInclusive(null, null, UserTestData.USER_ID), MealTestData.meals);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300), UserTestData.USER_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Meal(null, null, "Description", 300), UserTestData.USER_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 9), UserTestData.USER_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 5001), UserTestData.USER_ID));
    }
}
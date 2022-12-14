package ru.calories.nikolay.web.json;

import org.junit.jupiter.api.Test;
import ru.calories.nikolay.MealTestData;
import ru.calories.nikolay.UserTestData;
import ru.calories.nikolay.model.Meal;
import ru.calories.nikolay.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(MealTestData.adminMeal1);
        System.out.println(json);
        Meal meal = JsonUtil.readValue(json, Meal.class);
        MealTestData.MEAL_MATCHER.assertMatch(meal, MealTestData.adminMeal1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(MealTestData.meals);
        System.out.println(json);
        List<Meal> actual = JsonUtil.readValues(json, Meal.class);
        MealTestData.MEAL_MATCHER.assertMatch(actual, MealTestData.meals);
    }

    @Test
    void writeOnlyAccess() {
        String json = JsonUtil.writeValue(UserTestData.user);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.user, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}
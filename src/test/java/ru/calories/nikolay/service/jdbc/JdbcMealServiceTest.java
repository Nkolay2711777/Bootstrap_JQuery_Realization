package ru.calories.nikolay.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.calories.nikolay.service.AbstractMealServiceTest;

import static ru.calories.nikolay.Profiles.JDBC;

@ActiveProfiles(JDBC)
class JdbcMealServiceTest extends AbstractMealServiceTest {
}
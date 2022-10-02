package ru.calories.nikolay.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.calories.nikolay.service.AbstractUserServiceTest;

import static ru.calories.nikolay.Profiles.JDBC;

@ActiveProfiles(JDBC)
class JdbcUserServiceTest extends AbstractUserServiceTest {
}
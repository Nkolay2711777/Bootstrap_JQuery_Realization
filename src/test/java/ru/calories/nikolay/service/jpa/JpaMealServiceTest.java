package ru.calories.nikolay.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.calories.nikolay.service.AbstractMealServiceTest;

import static ru.calories.nikolay.Profiles.JPA;

@ActiveProfiles(JPA)
class JpaMealServiceTest extends AbstractMealServiceTest {
}
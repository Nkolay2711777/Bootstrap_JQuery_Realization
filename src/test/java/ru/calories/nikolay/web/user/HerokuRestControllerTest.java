package ru.calories.nikolay.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.calories.nikolay.TestUtil;
import ru.calories.nikolay.UserTestData;
import ru.calories.nikolay.util.exception.ErrorType;
import ru.calories.nikolay.util.exception.UpdateRestrictionException;
import ru.calories.nikolay.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.calories.nikolay.Profiles.HEROKU;

@ActiveProfiles(HEROKU)
class HerokuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + UserTestData.USER_ID)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(UpdateRestrictionException.EXCEPTION_UPDATE_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + UserTestData.USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(UserTestData.admin))
                .content(UserTestData.jsonWithPassword(UserTestData.user, "password")))
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(UpdateRestrictionException.EXCEPTION_UPDATE_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }
}

package com.musicshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicshop.dto.response.UserInfoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:db/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest extends ControllerTest {
    private final ObjectMapper objectMapper;
    private final MockMvc mvc;

    @Autowired
    public UserControllerTest(ObjectMapper objectMapper, MockMvc mvc) {
        this.objectMapper = objectMapper;
        this.mvc = mvc;
    }

    @WithMockUser(username = "Bob", authorities = "ROLE_CUSTOMER")
    @Test
    void getUserById_validLogin_success() throws Exception {
        String login = "Bob";
        String url = "/v2/users/" + login;
        UserInfoResponse expectedResponse = new UserInfoResponse(login, 5000);

        mvc.perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }
}

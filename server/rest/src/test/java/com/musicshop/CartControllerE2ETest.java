package com.musicshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicshop.dto.request.AddProductToCartRequest;
import com.musicshop.entity.AppUser;
import com.musicshop.security.SecurityUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:db/products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:db/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:db/cart.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class CartControllerE2ETest extends E2ETest {
    private final ObjectMapper objectMapper;
    private final MockMvc mvc;

    @Autowired
    public CartControllerE2ETest(ObjectMapper objectMapper, MockMvc mvc) {
        this.objectMapper = objectMapper;
        this.mvc = mvc;
    }

    @Test
    void addCartItem_validData_success() throws Exception {
        String login = "Bob";
        UUID productId = UUID.fromString("ab7acbcb-139a-41c4-90e1-fc42cc7d16e4");
        String url = "/v2/users/" + login + "/cart";
        AddProductToCartRequest request = new AddProductToCartRequest(productId);
        SecurityUser securityUser = new SecurityUser(new AppUser(2, login, "123", AppUser.Role.CUSTOMER));

        mvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .with(SecurityMockMvcRequestPostProcessors.user(securityUser))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void addCartItem_wrongProductId_success() throws Exception {
        String login = "Bob";
        UUID productId = UUID.fromString("aaaaaacb-139a-41c4-90e1-fc42cc7d16e4");
        String url = "/v2/users/" + login + "/cart";
        AddProductToCartRequest request = new AddProductToCartRequest(productId);
        SecurityUser securityUser = new SecurityUser(new AppUser(2, login, "123", AppUser.Role.CUSTOMER));

        mvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .with(SecurityMockMvcRequestPostProcessors.user(securityUser))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

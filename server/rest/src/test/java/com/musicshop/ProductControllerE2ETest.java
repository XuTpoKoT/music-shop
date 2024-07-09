package com.musicshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicshop.dto.response.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:db/products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ProductControllerE2ETest extends E2ETest {
    private final ObjectMapper objectMapper;
    private final MockMvc mvc;

    @Autowired
    public ProductControllerE2ETest(ObjectMapper objectMapper, MockMvc mvc) {
        this.objectMapper = objectMapper;
        this.mvc = mvc;
    }

    @Test
    void getProductById_validId_success() throws Exception {
        String productIdStr = "ab7acbcb-139a-41c4-90e1-fc42cc7d16e4";
        String url = "/v2/products/" + productIdStr;
        UUID productId = UUID.fromString(productIdStr);
        Map<String, String> characteristics = Map.of(
                "Материал корпуса", "Ясень",
                "Материал деки", "Ясень",
                "Кол-во ладов", "15");
        ProductResponse expectedProduct = ProductResponse.builder()
                .id(productId)
                .name("ELITARO ILG-3528")
                .price(31000)
                .color("Санберст")
                .imgRef("https://kombik.com/resources/img/000/001/822/img_182276.jpg")
                .manufacturerName("ELITARO")
                .characteristics(characteristics)
                .description("Гитара проходит предпродажный осмотр, что гарантирует получение качественного" +
                        " инструмента.             В комплекте с гитарой прилагается фирменный утепленный чехол," +
                        " который надежно защищает её.")
                .build();

        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedProduct)));
    }
}

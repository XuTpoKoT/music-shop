package com.musicshop;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@AutoConfigureMockMvc
@EnableWebMvc
@SpringBootTest
@ActiveProfiles("test")
abstract class ControllerTest extends PostgreSQLContainerTest {
}

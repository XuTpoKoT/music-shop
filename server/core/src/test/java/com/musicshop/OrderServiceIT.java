package com.musicshop;

import com.musicshop.dto.MakeOrderDto;
import com.musicshop.entity.AppUser;
import com.musicshop.entity.Order;
import com.musicshop.entity.OrderItem;
import com.musicshop.mapper.CartItemMapper;
import com.musicshop.mapper.CartItemMapperImpl;
import com.musicshop.repo.CartItemRepo;
import com.musicshop.repo.UserRepo;
import com.musicshop.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:db/products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/cart.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/pickup_points.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Import({OrderServiceImpl.class, CartItemMapperImpl.class})
public class OrderServiceIT extends DataJpaIT {
    private final OrderServiceImpl orderService;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepo cartItemRepo;
    private final UserRepo userRepo;
    private final JdbcTemplate jdbcTemplate;
    private final TestEntityManager testEntityManager;

    @Autowired
    public OrderServiceIT(OrderServiceImpl orderService, CartItemMapper cartItemMapper, CartItemRepo cartItemRepo,
                          UserRepo userRepo, JdbcTemplate jdbcTemplate, TestEntityManager testEntityManager) {
        this.orderService = orderService;
        this.cartItemMapper = cartItemMapper;
        this.cartItemRepo = cartItemRepo;
        this.userRepo = userRepo;
        this.jdbcTemplate = jdbcTemplate;
        this.testEntityManager = testEntityManager;
    }

    @Test
    public void makeOrder_customerWithoutSpendingBonuses_success() {
        Integer userId = 2;
        AppUser customer = userRepo.findById(userId).get();
        List<OrderItem> orderItems = cartItemMapper.cartItemsToOrderItems(
                cartItemRepo.findByUserId(customer.getId()));
        UUID pickUpPointId = UUID.fromString("b92f19d8-8db0-498f-8ee9-ce2580706708");
        boolean needSpendBonuses = false;
        MakeOrderDto makeOrderDto = MakeOrderDto.builder()
                .employeeId(null)
                .customer(customer)
                .timestamp(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("UTC")))
                .pickUpPointId(pickUpPointId)
                .needSpendBonuses(needSpendBonuses)
                .orderItems(orderItems)
                .status(Order.Status.formed)
                .build();
        Integer orderProductCountBefore = jdbcTemplate.queryForObject("select count (*) from order_product",
                Integer.class);
        Integer bonusesBefore = customer.getBonuses();

       orderService.makeOrder(makeOrderDto);
       testEntityManager.flush();

        Integer orderProductCountAfter = jdbcTemplate.queryForObject("select count (*) from order_product",
                Integer.class);
        Integer accruedBonuses = 8100;
        Integer bonusesAfter = jdbcTemplate.queryForObject("select bonuses from users where id = ?",
                Integer.class,
                customer.getId());
        assertTrue(cartItemRepo.findByUserId(customer.getId()).isEmpty());
        assertEquals(orderProductCountBefore + 2, orderProductCountAfter);
        assertEquals(bonusesBefore + accruedBonuses, bonusesAfter);
    }

    @Test
    public void makeOrder_customerWithSpendingBonuses_success() {
        Integer userId = 2;
        AppUser customer = userRepo.findById(userId).get();
        List<OrderItem> orderItems = cartItemMapper.cartItemsToOrderItems(
                cartItemRepo.findByUserId(customer.getId()));
        UUID pickUpPointId = UUID.fromString("b92f19d8-8db0-498f-8ee9-ce2580706708");
        boolean needSpendBonuses = true;
        MakeOrderDto makeOrderDto = MakeOrderDto.builder()
                .employeeId(null)
                .customer(customer)
                .timestamp(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("UTC")))
                .pickUpPointId(pickUpPointId)
                .needSpendBonuses(needSpendBonuses)
                .orderItems(orderItems)
                .status(Order.Status.formed)
                .build();
        Integer orderProductCountBefore = jdbcTemplate.queryForObject("select count (*) from order_product",
                Integer.class);
        Integer bonusesBefore = customer.getBonuses();

        orderService.makeOrder(makeOrderDto);
        testEntityManager.flush();

        Integer orderProductCountAfter = jdbcTemplate.queryForObject("select count (*) from order_product",
                Integer.class);
        Integer spentBonuses = 5000;
        Integer accruedBonuses = 7850;
        Integer bonusesAfter = jdbcTemplate.queryForObject("select bonuses from users where id = ?",
                Integer.class,
                customer.getId());
        assertTrue(cartItemRepo.findByUserId(customer.getId()).isEmpty());
        assertEquals(orderProductCountBefore + 2, orderProductCountAfter);
        assertEquals(bonusesBefore - spentBonuses + accruedBonuses, bonusesAfter);
    }
}

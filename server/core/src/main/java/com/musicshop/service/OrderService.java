package com.musicshop.service;


import com.musicshop.dto.MakeOrderDto;
import com.musicshop.entity.AppUser;
import com.musicshop.entity.Order;
import com.musicshop.entity.OrderItem;
import com.musicshop.entity.PickUpPoint;
import com.musicshop.repo.CartItemRepo;
import com.musicshop.repo.OrderRepo;
import com.musicshop.repo.PickUpPointRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Math.min;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService{
    @Value("${bonuses.max-spend-percent}")
    private int MAX_PERCENTS_SPENT_BONUSES;
    @Value("${bonuses.accrued-percent}")
    private int BONUS_PERCENT;
    private final OrderRepo orderRepo;
    private final PickUpPointRepo pickUpPointRepo;
    private final CartItemRepo cartItemRepo;

    @Transactional
    public void makeOrder(MakeOrderDto makeOrderDto) {
        AppUser customer = makeOrderDto.customer();
        int initialCost = calcInitialCost(makeOrderDto.orderItems());
        PickUpPoint pickUpPoint = pickUpPointRepo.findById(makeOrderDto.pickUpPointId()).orElseThrow(
                () -> new EntityNotFoundException("pickUpPoint " + makeOrderDto.pickUpPointId() + " not found"));

        int paidByBonuses = 0;
        if (customer != null) {
            if (makeOrderDto.needSpendBonuses()) {
                paidByBonuses = calcSpentBonuses(customer, initialCost);
            }
            int accruedBonuses = calcAccruedBonuses(initialCost - paidByBonuses);
            customer.incBonuses(accruedBonuses - paidByBonuses);
        }

        Order order = Order.builder()
                .employeeId(makeOrderDto.employeeId())
                .customer(customer)
                .timestamp(makeOrderDto.timestamp())
                .pickUpPoint(pickUpPoint)
                .status(makeOrderDto.status())
                .initialCost(initialCost)
                .paidByBonuses(paidByBonuses)
                .build();
        order.setOrderItems(makeOrderDto.orderItems());
        orderRepo.save(order);
        cleanCart(makeOrderDto);
    }

    private int calcInitialCost(List<OrderItem> orderItems) {
        log.debug("calcInitialCost called");
        int initialCost = 0;
        for (OrderItem orderItem : orderItems) {
            initialCost += orderItem.getCount() * orderItem.getPrice();
        }
        return initialCost;
    }
    private int calcSpentBonuses(AppUser customer, int initialCost) {
        log.debug("calcSpentBonuses called with customer " + customer.getUsername() + " and initial cost "
            + initialCost);
        int maxSpentBonuses = MAX_PERCENTS_SPENT_BONUSES * initialCost / 100;
        return min(customer.getBonuses(), maxSpentBonuses);
    }
    private int calcAccruedBonuses(int realCost) {
        log.debug("calcAccruedBonuses called with realCost " + realCost);
        return BONUS_PERCENT * realCost / 100;
    }
    private void cleanCart(MakeOrderDto makeOrderDto) {
        Integer userId = makeOrderDto.employeeId();
        if (userId == null) {
            userId = makeOrderDto.customer().getId();
        }
        log.debug("cleanCart called with login" + userId);
        cartItemRepo.deleteByUserId(userId);
    }
}

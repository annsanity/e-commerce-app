package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.OrderException;
import com.ecommerce.projectapp.model.OrderItem;
import com.ecommerce.projectapp.repository.OrderItemRepository;
import com.ecommerce.projectapp.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem getOrderItemById(Long id) throws Exception {

        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if(orderItem.isPresent()){
            return orderItem.get();
        }
        throw new OrderException("Order item not found");
    }
}


package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.OrderException;
import com.ecommerce.projectapp.model.OrderItem;
import com.ecommerce.projectapp.repository.OrderItemRepository;
import com.ecommerce.projectapp.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem getOrderItemById(Long id) throws OrderException {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderException("Order item not found with id: " + id));
    }
}


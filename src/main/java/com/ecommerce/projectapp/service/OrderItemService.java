package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.model.OrderItem;

public interface OrderItemService {

    OrderItem getOrderItemById(Long id) throws Exception;
}

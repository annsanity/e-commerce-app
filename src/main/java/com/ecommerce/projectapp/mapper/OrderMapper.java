package com.ecommerce.projectapp.mapper;

import com.ecommerce.projectapp.dto.OrderDto;
import com.ecommerce.projectapp.dto.OrderHistory;
import com.ecommerce.projectapp.dto.OrderItemDto;
import com.ecommerce.projectapp.model.OrderItem;
import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.domain.OrderStatus;
import com.ecommerce.projectapp.model.User;


import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderItemDto toOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProduct(ProductMapper.toProductDto(orderItem.getProduct()));
        orderItemDto.setSize(orderItem.getSize());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setMrpPrice(orderItem.getMrpPrice());
        orderItemDto.setSellingPrice(orderItem.getSellingPrice());
        return orderItemDto;
    }

    public static OrderItem toOrderItem(OrderItemDto orderItemDto){
        if (orderItemDto == null) {
            return null;
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDto.getId());
//        orderItem.setProduct(ProductMapper.toProductDto(orderItemDto.getProduct()));
        orderItem.setSize(orderItemDto.getSize());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setMrpPrice(orderItemDto.getMrpPrice());
        orderItem.setSellingPrice(orderItemDto.getSellingPrice());

        // Assuming that the User is fetched separately and set here
        return orderItem;
    }

    public static OrderDto toOrderDto(Order order){
        if(order == null){
            return null;
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderId(order.getOrderId());
        orderDto.setUser(UserMapper.toUserDto(order.getUser()));
        orderDto.setSellerId(order.getSellerId());
        orderDto.setOrderItems(order.getOrderItems().stream().map(OrderMapper::toOrderItemDto).collect(Collectors.toList()));
        orderDto.setShippingAddress(order.getShippingAddress());
        orderDto.setPaymentDetails(order.getPaymentDetails());
        orderDto.setTotalMrpPrice(order.getTotalMrpPrice());
        orderDto.setTotalSellingPrice(order.getTotalSellingPrice());
        orderDto.setDiscount(order.getDiscount());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setTotalItem(order.getTotalItem());
        orderDto.setPaymentStatus(order.getPaymentStatus());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setDeliverDate(order.getDeliverDate());

        return orderDto;
    }

    public static Order toOrder(OrderDto orderDto){
        if(orderDto == null){
            return null;
        }

        Order order = new Order();
        order.setId(orderDto.getId());
        order.setOrderId(orderDto.getOrderId());
        order.setSellerId(orderDto.getSellerId());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setPaymentDetails(orderDto.getPaymentDetails());
        order.setTotalMrpPrice(orderDto.getTotalMrpPrice());
        order.setTotalSellingPrice(orderDto.getTotalSellingPrice());
        order.setDiscount(orderDto.getDiscount());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setTotalItem(orderDto.getTotalItem());
        order.setPaymentStatus(orderDto.getPaymentStatus());
        order.setOrderDate(orderDto.getOrderDate());
        order.setDeliverDate(orderDto.getDeliverDate());

        return order;

    }

    public static OrderHistory toOrderHistory(List<Order> orders, User user) {
        if (orders == null || user == null) {
            return null;
        }

        OrderHistory orderHistory = new OrderHistory();

        // Set user
        orderHistory.setUser(UserMapper.toUserDto(user));

        // Filter current orders (those that are not DELIVERED or CANCELLED)
        List<OrderDto> currentOrders = orders.stream()
                .filter(order -> order.getOrderStatus() != OrderStatus.DELIVERED && order.getOrderStatus() != OrderStatus.CANCELLED)
                .map(OrderMapper::toOrderDto)
                .collect(Collectors.toList());

        orderHistory.setCurrentOrders(currentOrders);

        // Set total orders
        orderHistory.setTotalOrders(orders.size());

        // Set cancelled orders
        int cancelledOrders = (int) orders.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.CANCELLED)
                .count();
        orderHistory.setCancelledOrders(cancelledOrders);

        // Set completed orders (those that are DELIVERED)
        int completedOrders = (int) orders.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.DELIVERED)
                .count();
        orderHistory.setCompletedOrders(completedOrders);


        return orderHistory;
    }



}



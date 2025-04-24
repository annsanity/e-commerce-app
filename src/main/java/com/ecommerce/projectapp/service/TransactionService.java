package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.model.Transaction;

public interface TransactionService {

    Transaction createTransaction(Order order);

    Transaction findTransactionById(Long id);

    Transaction findTransactionByOrderId(Long orderId);
}
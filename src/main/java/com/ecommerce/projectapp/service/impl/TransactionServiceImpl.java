package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.model.Transaction;
import com.ecommerce.projectapp.repository.TransactionRepository;
import com.ecommerce.projectapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Order order) {
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        transaction.setCustomer(order.getUser());
        // No seller reference needed in our streamlined version

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
    }

    @Override
    public Transaction findTransactionByOrderId(Long orderId) {
        return transactionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Transaction not found for order ID: " + orderId));
    }
}
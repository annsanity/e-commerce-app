package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.model.Seller;
import com.ecommerce.projectapp.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionBySeller(Seller seller);
    List<Transaction>getAllTransactions();
}

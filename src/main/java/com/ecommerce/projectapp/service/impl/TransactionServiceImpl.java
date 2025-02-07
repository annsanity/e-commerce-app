package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.model.Seller;
import com.ecommerce.projectapp.model.Transaction;
import com.ecommerce.projectapp.repository.SellerRepository;
import com.ecommerce.projectapp.repository.TransactionRepository;
import com.ecommerce.projectapp.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;


    @Override
    public Transaction createTransaction(Order order) {

        Seller seller = sellerRepository.findById(order.getSellerId()).get();
        Transaction transaction = new Transaction();
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);
        transaction.setSeller(seller);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionBySeller(Seller seller) {
        return transactionRepository.findBySeller_Id(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}

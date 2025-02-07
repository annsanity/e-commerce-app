package com.ecommerce.projectapp.repository;


import com.ecommerce.projectapp.model.Transaction;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findBySeller_Id(Long sellerId);
}

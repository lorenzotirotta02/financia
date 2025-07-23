package com.financia.financia.repository;

import com.financia.financia.model.Transaction;
import com.financia.financia.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select t from Transaction t where t.type = ?1")
    List<Transaction> findAllByTransactionType(TransactionType transactionType);
}

package com.financia.financia.repository;

import com.financia.financia.model.TransactionSum;
import com.financia.financia.model.pkclass.TransactionSumPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionSumRepository extends JpaRepository<TransactionSum, TransactionSumPrimaryKey> {

    List<TransactionSum> findByCategoryAndUserId(String category, Long userId);
    List<TransactionSum> findAllByUserId(Long userId);
}

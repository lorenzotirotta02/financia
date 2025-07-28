package com.financia.financia.service.abstraction;

import com.financia.financia.model.TransactionSum;
import com.financia.financia.model.pkclass.TransactionSumPrimaryKey;

import java.util.List;

public interface IViewService {

    List<TransactionSum> getTransactionsSumByUserAndCategory(String category);
    List<TransactionSum> getAllTransactions();
}


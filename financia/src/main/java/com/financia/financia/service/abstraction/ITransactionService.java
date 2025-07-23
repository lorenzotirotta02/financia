package com.financia.financia.service.abstraction;

import com.financia.financia.dto.TransactionDTO;
import com.financia.financia.model.Transaction;
import com.financia.financia.model.TransactionType;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;

public interface ITransactionService {

    Transaction createTransaction(TransactionDTO transaction);

    BigDecimal sumTransactionsByType(TransactionType transactionType);
}

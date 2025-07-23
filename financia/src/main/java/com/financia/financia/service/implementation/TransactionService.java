package com.financia.financia.service.implementation;

import com.financia.financia.dto.TransactionDTO;
import com.financia.financia.exception.UserNonTrovatoException;
import com.financia.financia.model.Transaction;
import com.financia.financia.model.TransactionType;
import com.financia.financia.model.User;
import com.financia.financia.repository.TransactionRepository;
import com.financia.financia.repository.UserRepository;
import com.financia.financia.service.abstraction.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Transaction createTransaction(TransactionDTO transaction) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UserNonTrovatoException("User not found")
        ));

    }

    @Override
    public BigDecimal sumTransactionsByType(TransactionType transactionType) {
        return null;
    }

}

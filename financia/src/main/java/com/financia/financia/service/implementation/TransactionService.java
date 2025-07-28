package com.financia.financia.service.implementation;

import com.financia.financia.dto.TransactionDTO;
import com.financia.financia.exception.UserNonTrovatoException;
import com.financia.financia.mapper.TransactionMapper;
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
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public Transaction createTransaction(TransactionDTO transaction) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UserNonTrovatoException("User not found")
        ));
        if(transaction != null && user.isPresent()){
            Transaction newTransaction = new Transaction();
            newTransaction.setAmount(transaction.getAmount());
            newTransaction.setDescription(transaction.getDescription());
            newTransaction.setType(TransactionType.valueOf(transaction.getType()));
            newTransaction.setUser(user.get());
            return transactionRepository.save(newTransaction);
        } else {
            throw new RuntimeException("Il dto è vuoto o l'utente non è presente");
        }

    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByEmail(authentication.getName());
        if(user.isPresent()) {
            Optional<Transaction> transaction = transactionRepository.findById(transactionId);
            if (transaction.get().getUser().getId().equals(user.get().getId())) {
            transactionRepository.deleteById(transactionId);
            } else {
                throw new RuntimeException("Transaction does not belong to the user");
            }
        }
        throw new UserNonTrovatoException("User not found");
    }

    @Override
    public void updateTransaction(TransactionDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOpt = userRepository.findByEmail(authentication.getName());

        if (userOpt.isEmpty()) {
            throw new UserNonTrovatoException("User not found");
        }

        Optional<Transaction> transactionOpt = transactionRepository.findById(dto.getId());

        if (transactionOpt.isEmpty()) {
            throw new RuntimeException("Transaction not found");
        }

        Transaction existingTransaction = transactionOpt.get();

        // Verifica che l'utente loggato sia il proprietario della transazione
        if (!existingTransaction.getUser().getId().equals(userOpt.get().getId())) {
            throw new RuntimeException("Transaction does not belong to the current user");
        }

        // Esegui update parziale (solo campi non null)
        transactionMapper.updateTransactionFromDTO(dto, existingTransaction);

        // Salva le modifiche
        transactionRepository.save(existingTransaction);
    }



}

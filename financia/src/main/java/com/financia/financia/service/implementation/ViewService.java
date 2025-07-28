package com.financia.financia.service.implementation;

import com.financia.financia.exception.UserNonTrovatoException;
import com.financia.financia.model.TransactionSum;
import com.financia.financia.model.User;
import com.financia.financia.repository.TransactionSumRepository;
import com.financia.financia.repository.UserRepository;
import com.financia.financia.service.abstraction.IViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewService implements IViewService {

    private final TransactionSumRepository transactionSumRepository;
    private final UserRepository userRepository;

    @Override
    public List<TransactionSum> getTransactionsSumByUserAndCategory(String category) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNonTrovatoException("User not found")));
        if(user.isEmpty() || category == null){
            throw new RuntimeException("User not found or category is null");
        } else {
            return transactionSumRepository.findByCategoryAndUserId(category, user.get().getId());
        }
    }

    @Override
    public List<TransactionSum> getAllTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNonTrovatoException("User not found")));
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        } else {
            return transactionSumRepository.findAllByUserId(user.get().getId());
        }

    }
}

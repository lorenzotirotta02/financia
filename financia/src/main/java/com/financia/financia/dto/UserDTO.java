package com.financia.financia.dto;

import com.financia.financia.model.Budget;
import com.financia.financia.model.Goal;
import com.financia.financia.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String email;

    private String password;

    private List<Transaction> transactions;

    private List<Goal> goals;

    private List<Budget> budgets;
}

package com.financia.financia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {

    private Long id;

    private String month;

    private String category;

    private BigDecimal limitAmount;

    private BigDecimal spentAmount;

    private Long userId;

}

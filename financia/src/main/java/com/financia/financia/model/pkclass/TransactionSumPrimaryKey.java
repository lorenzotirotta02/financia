package com.financia.financia.model.pkclass;

import com.financia.financia.model.TransactionSum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class TransactionSumPrimaryKey  implements Serializable {
    private Long user_id;
    private String category;
    private BigDecimal sumCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionSumPrimaryKey that = (TransactionSumPrimaryKey) o;
        return Objects.equals(user_id, that.user_id) && Objects.equals(category, that.category) && Objects.equals(sumCount, that.sumCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, category, sumCount);
    }

}

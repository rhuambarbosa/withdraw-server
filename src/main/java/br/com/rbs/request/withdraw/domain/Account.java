package br.com.rbs.request.withdraw.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Account {

    @Id
    private String accountId;

    private BigDecimal availableAmount;

    private boolean blockBalance;

    public Account() {
    }

    public void Withdraw(BigDecimal amount) {
        this.availableAmount = this.availableAmount.subtract(amount);
    }

    public boolean isWithdraw(BigDecimal amount) {
        this.blockBalance = Boolean.FALSE;
        BigDecimal balance = this.availableAmount.subtract(amount);
        return balance.compareTo(BigDecimal.ZERO) >= 0;
    }
}

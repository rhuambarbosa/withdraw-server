package br.com.rbs.request.withdraw.domain;

import br.com.rbs.request.withdraw.enuns.WithdrawEnum;
import br.com.rbs.request.withdraw.utils.SecureRandomNumber;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    @Id
    private String authorizationCode;

    private String accountId;

    private String action;

    private String code;

    private BigDecimal amount;

    private LocalDateTime creationDate;

    public Transaction() {
        this.creationDate = LocalDateTime.now();
        this.authorizationCode = SecureRandomNumber.generateAuthorizationCode();
    }

    public Transaction(WithdrawEnum withdrawEnum) {
        this();
        this.code = withdrawEnum.getCode();
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setCode(WithdrawEnum withdrawEnum) {
        this.code = withdrawEnum.getCode();
    }
}
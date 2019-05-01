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
    private String traceCode;

    private String authorizationCode;

    private String accountId;

    private String action;

    private String code;

    private BigDecimal amount;

    private LocalDateTime creationDate;

    public Transaction() {
        this.traceCode = SecureRandomNumber.generateAuthorizationCode();
        this.creationDate = LocalDateTime.now();
    }

    public Transaction(WithdrawEnum withdrawEnum, String authorizationCode) {
        this();
        this.code = withdrawEnum.getCode();
        this.authorizationCode = authorizationCode;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setCode(WithdrawEnum withdrawEnum) {
        this.code = withdrawEnum.getCode();
    }
}
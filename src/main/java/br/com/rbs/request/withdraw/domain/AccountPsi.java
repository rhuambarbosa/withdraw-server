package br.com.rbs.request.withdraw.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class AccountPsi {

    @Id
    private Long cardNumber;

    private String accountId;

    private boolean enable;

    public AccountPsi() {
    }
}
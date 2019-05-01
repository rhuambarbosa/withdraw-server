package br.com.rbs.request.withdraw.dto;

import br.com.rbs.request.withdraw.domain.Transaction;
import lombok.Data;

@Data
public class ResponseDto {

    private String action;

    private String code;

    private String authorization_code;

    public ResponseDto(Transaction transaction) {
        this.action = transaction.getAction();
        this.code = transaction.getCode();
        this.authorization_code = transaction.getAuthorizationCode();
    }
}
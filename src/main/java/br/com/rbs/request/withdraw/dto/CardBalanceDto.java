package br.com.rbs.request.withdraw.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CardBalanceDto {

    private Long cardnumber;

    private BigDecimal availableAmount;

    private List<TransactionDto> transactions;

}

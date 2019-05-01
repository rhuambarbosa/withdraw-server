package br.com.rbs.request.withdraw.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {

    private LocalDateTime date;

    private BigDecimal amount;

    public TransactionDto(LocalDateTime date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }
}

package br.com.rbs.request.withdraw.dto;

import br.com.rbs.request.withdraw.utils.NumberUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class TransactionDto {

    @JsonIgnore
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String date;

    private String amount;

    public TransactionDto(LocalDateTime date, BigDecimal amount) {
        this.date = date.format(formatter);
        this.amount = NumberUtils.formatNumber(amount);
    }
}

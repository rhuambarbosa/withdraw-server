package br.com.rbs.request.withdraw.dto;

import br.com.rbs.request.withdraw.utils.NumberUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CardBalanceDto {

    private Long cardnumber;

    private String availableAmount;

    private List<TransactionDto> transactions;

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = NumberUtils.formatNumber(availableAmount);
    }
}

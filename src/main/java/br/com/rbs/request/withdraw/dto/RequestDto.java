package br.com.rbs.request.withdraw.dto;

import br.com.rbs.request.withdraw.enuns.ActionEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class RequestDto {

    private String action;

    private Long cardnumber;

    private String amount;

    public boolean isWithdraw() {
        return ActionEnum.WITHDRAW.getAction().equals(action);
    }

    public BigDecimal getAmount() throws ParseException {
        return new BigDecimal(converte(amount)).setScale(2, RoundingMode.HALF_EVEN);
    }

    private double converte(String arg) throws ParseException {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        return nf.parse(arg).doubleValue();
    }

    public Long getCardnumber() {
        return cardnumber;
    }

    public String getAction() {
        return action;
    }
}
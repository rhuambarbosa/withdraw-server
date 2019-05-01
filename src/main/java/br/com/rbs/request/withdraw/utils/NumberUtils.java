package br.com.rbs.request.withdraw.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {

    public static String formatNumber(BigDecimal amount) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        return df.format(amount);
    }
}

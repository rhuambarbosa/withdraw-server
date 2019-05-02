package br.com.rbs.request.withdraw.service;


import br.com.rbs.request.withdraw.domain.Transaction;
import br.com.rbs.request.withdraw.dto.RequestDto;
import br.com.rbs.request.withdraw.exception.RequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.text.ParseException;

import static org.testng.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ValidationServiceTest {

    @InjectMocks
    private ValidationService validationServiceMock;

    @Test
    public void getRequestDtoSuccess() throws RequestException, ParseException {
        String json = "{\"action\": \"withdraw\",\"cardnumber\":\"1234567890123456\",\"amount\": \"1,10\"}";
        String actionExpeted = "withdraw";
        Long carNumberExpeted = 1234567890123456L;
        BigDecimal amountExpected = new BigDecimal("1.10");

        RequestDto requestDto = validationServiceMock.getRequestDto(json, new Transaction());

        assertTrue(actionExpeted.equals(requestDto.getAction()), "Action não compatível.");
        assertTrue(carNumberExpeted.equals(requestDto.getCardnumber()), "Cardnumber não compatível.");
        assertTrue(amountExpected.equals(requestDto.getAmount()), "Amount não compatível.");
    }
}
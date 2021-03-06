package br.com.rbs.request.withdraw.service;

import br.com.rbs.request.withdraw.domain.Transaction;
import br.com.rbs.request.withdraw.dto.RequestDto;
import br.com.rbs.request.withdraw.exception.RequestException;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawService.class);

    public RequestDto getRequestDto(String message, Transaction transaction) throws RequestException {
        LOGGER.info("WithDrawServer-WithdrawService:Validação da mensagem traceCode={}", transaction.getTraceCode());

        RequestDto requestDto;

        try {
            requestDto = new Gson().fromJson(message, RequestDto.class);
            transaction.setAction(requestDto.getAction());
            transaction.setAmount(requestDto.getAmount());
        } catch (Exception e) {
            LOGGER.info("WithDrawServer-WithdrawService:Falha ao converter a mensagem. traceCode={}", transaction.getTraceCode());
            throw new RequestException("Invalid data");
        }

        if (requestDto.isWithdraw()) {
            LOGGER.info("WithDrawServer-WithdrawService:Mensagem válida. traceCode={}", transaction.getTraceCode());
            return requestDto;
        }

        LOGGER.info("WithDrawServer-WithdrawService:Ação inválida. traceCode={}", transaction.getTraceCode());
        throw new RequestException("Invalid Action");
    }
}
package br.com.rbs.request.withdraw.service;

import br.com.rbs.request.withdraw.domain.Transaction;
import br.com.rbs.request.withdraw.enuns.RabbitQueue;
import br.com.rbs.request.withdraw.repository.TransactionRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TransactionRepository repository;

    public void postTransaction(Transaction transaction) {
        LOGGER.info("WithDrawServer-TransactionService:Postando mensagem na fila traceCode={}", transaction.getTraceCode());
        rabbitTemplate.convertAndSend(RabbitQueue.REGISTER_TRANSACTION.getQueue(), new Gson().toJson(transaction));
    }

    public List<Transaction> findByAccountIdAndAction(String accountId, String action, String code, Pageable pageable) {
        Page<Transaction> page = repository.findByAccountIdAndActionAndCode(accountId, action, code, pageable);
        return page.getContent();
    }
}
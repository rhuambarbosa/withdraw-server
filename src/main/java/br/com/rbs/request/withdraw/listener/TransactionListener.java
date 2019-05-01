package br.com.rbs.request.withdraw.listener;

import br.com.rbs.request.withdraw.domain.Transaction;
import br.com.rbs.request.withdraw.repository.TransactionRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionListener.class);

    private static final String REGISTER_TRANSACTION = "register.transaction";

    @Autowired
    private TransactionRepository repository;

    @RabbitListener(queues = REGISTER_TRANSACTION)
    public void onMessage(String message) {
        Transaction transaction = null;

        try {
            LOGGER.info("WithDrawServer-TransactionListener:Iniciando converção da mensagem.....");

            transaction = new Gson().fromJson(message, Transaction.class);
            LOGGER.info("WithDrawServer-TransactionListener:Mensagem recebida atraceCode={}", transaction.getTraceCode());

            LOGGER.info("WithDrawServer-TransactionService:Iniciando salvamento da transação traceCode={}", transaction.getTraceCode());
            repository.save(transaction);

            LOGGER.info("WithDrawServer-TransactionService:Transação salva com sucesso. traceCode={}", transaction.getTraceCode());
        } catch (Exception e) {
            if (transaction == null) {
                LOGGER.info("WithDrawServer-TransactionListener:Falha ao converter mensagem...");
            } else {
                LOGGER.info("WithDrawServer-TransactionListener:Falha no salvamento da mensagem traceCode={}", transaction.getTraceCode());
            }
        }
    }
}
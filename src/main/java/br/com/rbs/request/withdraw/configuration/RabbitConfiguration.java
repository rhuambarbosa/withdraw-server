package br.com.rbs.request.withdraw.configuration;

import br.com.rbs.request.withdraw.enuns.RabbitQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    @Bean
    public Queue queue() {
        return new Queue(RabbitQueue.REGISTER_TRANSACTION.getQueue(), true);
    }
}

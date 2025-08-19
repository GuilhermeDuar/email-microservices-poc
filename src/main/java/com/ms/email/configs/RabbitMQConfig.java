package com.ms.email.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // tanto a propriedade queue quanto a função queue() tem como objetivo criar a fila no broker,
    // sendo necessário realizar isso apenas em um dos ms

    //Consulta o valor definido no .properties
    @Value("${broker.queue.email.name}")
    private String queue;

    @Bean
    public Queue queue(){
        return new Queue(queue,true);
    }

    @Bean
    public Jackson2JsonMessageConverter massageConverter(){
        ObjectMapper mapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(mapper);
    }

}

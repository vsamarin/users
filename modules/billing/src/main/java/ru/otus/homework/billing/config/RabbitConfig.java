package ru.otus.homework.billing.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    public static final String ACCOUNT_QUEUE = "queue.account";
    public static final String ACCOUNT_QUEUE_REPLY = "queue.account.reply";
    public static final String ACCOUNT_EXCHANGE = "exchange.account";

    public static final String WITHDRAW_QUEUE = "queue.withdraw";
    public static final String WITHDRAW_QUEUE_REPLY = "queue.withdraw.reply";
    public static final String WITHDRAW_EXCHANGE = "exchange.withdraw";

    private final RabbitProperties properties;

    @Bean
    public RabbitAdmin admin() {
        return new RabbitAdmin(rabbitConnectionFactory());
    }

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(properties.getHost());
        connectionFactory.setPort(properties.getPort());
        connectionFactory.setUsername(properties.getUsername());
        connectionFactory.setPassword(properties.getPassword());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory());
        template.setMessageConverter(converter());
        return template;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(ACCOUNT_EXCHANGE);
    }

    @Bean
    public DirectExchange withdrawExchange() {
        return new DirectExchange(WITHDRAW_EXCHANGE);
    }

    @Bean
    public Queue requestQueue() {
        return new Queue(ACCOUNT_QUEUE);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue(ACCOUNT_QUEUE_REPLY);
    }

    @Bean
    public Queue withdrawQueue() {
        return new Queue(WITHDRAW_QUEUE);
    }

    @Bean
    public Queue withdrawReplyQueue() {
        return new Queue(WITHDRAW_QUEUE_REPLY);
    }

    @Bean
    public Binding requestBinding() {
        return BindingBuilder.bind(requestQueue()).to(exchange()).with(ACCOUNT_QUEUE);
    }

    @Bean
    public Binding replyBinding() {
        return BindingBuilder.bind(replyQueue()).to(exchange()).with(ACCOUNT_QUEUE_REPLY);
    }

    @Bean
    public Binding withdrawBinding() {
        return BindingBuilder.bind(withdrawQueue()).to(withdrawExchange()).with(WITHDRAW_QUEUE);
    }

    @Bean
    public Binding withdrawReplyBinding() {
        return BindingBuilder.bind(withdrawReplyQueue()).to(withdrawExchange()).with(WITHDRAW_QUEUE_REPLY);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter(objectMapper());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}

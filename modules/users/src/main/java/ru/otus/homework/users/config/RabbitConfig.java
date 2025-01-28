package ru.otus.homework.users.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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
        template.setExchange(exchange().getName());
        //template.setRoutingKey("test");
        template.setReplyAddress(ACCOUNT_QUEUE_REPLY);
        //template.setReceiveTimeout(10);
        template.setReplyTimeout(30000);
        template.setMessageConverter(converter());
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitConnectionFactory());
        container.setQueues(replyQueue());
        container.setReceiveTimeout(10);
        container.setMessageListener(rabbitTemplate());
        return container;
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
    public DirectExchange exchange() {
        return new DirectExchange(ACCOUNT_EXCHANGE);
    }

    @Bean
    public Binding msgBinding() {
        return BindingBuilder.bind(requestQueue()).to(exchange()).with(ACCOUNT_QUEUE);
    }

    @Bean
    public Binding replyBinding() {
        return BindingBuilder.bind(replyQueue()).to(exchange()).with(ACCOUNT_QUEUE_REPLY);
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

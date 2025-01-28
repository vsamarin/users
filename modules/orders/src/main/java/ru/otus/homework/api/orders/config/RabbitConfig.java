package ru.otus.homework.api.orders.config;

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

    public static final String WITHDRAW_QUEUE = "queue.withdraw";
    public static final String WITHDRAW_QUEUE_REPLY = "queue.withdraw.reply";
    public static final String WITHDRAW_EXCHANGE = "exchange.withdraw";

    public static final String NOTIFICATION_QUEUE = "queue.notification";
    public static final String NOTIFICATION_EXCHANGE = "exchange.notification";

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
        template.setReplyAddress(WITHDRAW_QUEUE_REPLY);
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
        return new Queue(WITHDRAW_QUEUE);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue(WITHDRAW_QUEUE_REPLY);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(WITHDRAW_EXCHANGE);
    }

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding msgBinding() {
        return BindingBuilder.bind(requestQueue()).to(exchange()).with(WITHDRAW_QUEUE);
    }

    @Bean
    public Binding replyBinding() {
        return BindingBuilder.bind(replyQueue()).to(exchange()).with(WITHDRAW_QUEUE_REPLY);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue()).to(notificationExchange()).with(NOTIFICATION_QUEUE);
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

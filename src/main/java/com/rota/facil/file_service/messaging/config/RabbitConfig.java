package com.rota.facil.file_service.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.file.exchange}")
    private String fileExchange;

    
    @Value("${rabbitmq.auth.exchange}")
    private String authExchange;

    @Value("${rabbitmq.file.user.deleted.queue}")
    private String userDeletedQueue;

    @Value("${rabbitmq.file.prefecture.deleted.queue}")
    private String prefectureDeletedQueue;

    @Value("${rabbitmq.file.user.deleted.routing.key}")
    private String userDeletedRoutingKey;

    @Value("${rabbitmq.file.prefecture.deleted.routing.key}")
    private String prefectureDeletedRoutingKey;



    @Value("${rabbitmq.places.exchange}")
    private String placesExchange;

    @Value("${rabbitmq.file.institution.deleted.queue}")
    private String institutionDeletedQueue;

    @Value("${rabbitmq.file.boarding.deleted.queue}")
    private String boardingDeletedQueue;

    @Value("${rabbitmq.file.institution.deleted.routing.key}")
    private String institutionDeletedRoutingKey;

    @Value("${rabbitmq.file.boarding.deleted.routing.key}")
    private String boardingDeletedRoutingKey;



    @Value("${rabbitmq.transport.exchange}")
    private String transportExchange;

    @Value("${rabbitmq.file.bus.deleted.queue}")
    private String busDeletedQueue;

    @Value("${rabbitmq.file.bus.deleted.routing.key}")
    private String busDeletedRoutingKey;

    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter
    ) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter
    ) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        simpleRabbitListenerContainerFactory.setMessageConverter(messageConverter);
        return simpleRabbitListenerContainerFactory;
    }

    @Bean
    public TopicExchange fileExchange() {
        return new TopicExchange(fileExchange);
    }

    @Bean
    public TopicExchange authExchange() {
        return new TopicExchange(authExchange);
    }

    @Bean
    public TopicExchange placesExchange() {
        return new TopicExchange(placesExchange);
    }

    @Bean
    public TopicExchange transportExchange() {
        return new TopicExchange(transportExchange);
    }

    @Bean
    public Queue userDeletedQueue() {
        return new Queue(userDeletedQueue);
    }

    @Bean
    public Queue prefectureDeletedQueue() {
        return new Queue(prefectureDeletedQueue);
    }

    @Bean
    public Queue institutionDeletedQueue() {
        return new Queue(institutionDeletedQueue);
    }

    @Bean
    public Queue boardingDeletedQueue() {
        return new Queue(boardingDeletedQueue);
    }

    @Bean
    public Queue busDeletedQueue() {
        return new Queue(busDeletedQueue);
    }

    @Bean
    public Binding deleteUserBinding() {
        return BindingBuilder.bind(this.userDeletedQueue()).to(this.authExchange()).with(userDeletedRoutingKey);
    }

    @Bean
    public Binding deletePrefectureBinding() {
        return BindingBuilder.bind(this.prefectureDeletedQueue()).to(this.authExchange()).with(prefectureDeletedRoutingKey);
    }

    @Bean
    public Binding deleteInstitutionBinding() {
        return BindingBuilder.bind(this.institutionDeletedQueue()).to(this.placesExchange()).with(institutionDeletedRoutingKey);
    }

    @Bean
    public Binding deleteBoardingBinding() {
        return BindingBuilder.bind(this.boardingDeletedQueue()).to(this.placesExchange()).with(boardingDeletedRoutingKey);
    }

    @Bean
    public Binding deleteTransportBinding() {
        return BindingBuilder.bind(this.busDeletedQueue()).to(this.transportExchange()).with(busDeletedRoutingKey);
    }
}


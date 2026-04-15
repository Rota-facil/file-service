package com.rota.facil.file_service.messaging.producers;

import com.rota.facil.file_service.messaging.dto.send.FileEventSend;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitAuditEventProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.file.exchange}")
    private String fileExchange;

    @Value("${rabbitmq.file.created.routing.key}")
    private String fileCreatedRoutingKet;

    @Value("${rabbitmq.file.updated.routing.key}")
    private String fileUpdatedRoutingKet;

    @Value("${rabbitmq.file.deleted.routing.key}")
    private String fileDeletedRoutingKet;

    public void createFileEvent(FileEventSend fileEventSend) {
        rabbitTemplate.convertAndSend(fileExchange, fileCreatedRoutingKet, fileEventSend);
    }

    public void updateFileEvent(FileEventSend fileEventSend) {
        rabbitTemplate.convertAndSend(fileExchange, fileUpdatedRoutingKet, fileEventSend);
    }

    public void deleteFileEvent(FileEventSend fileEventSend) {
        rabbitTemplate.convertAndSend(fileExchange, fileDeletedRoutingKet, fileEventSend);
    }
}

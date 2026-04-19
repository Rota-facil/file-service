package com.rota.facil.file_service.messaging.producers;

import com.rota.facil.file_service.domain.enums.ActionType;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.messaging.dto.send.AuditFileEventSend;
import com.rota.facil.file_service.messaging.dto.send.FileEventSend;
import com.rota.facil.file_service.messaging.mappers.FileEventMapper;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitFileEventProducer {
    private final RabbitTemplate rabbitTemplate;
    private final FileEventMapper fileEventMapper;

    @Value("${rabbitmq.file.exchange}")
    private String fileExchange;

    @Value("${rabbitmq.file.created.routing.key}")
    private String fileCreatedRoutingKet;

    @Value("${rabbitmq.file.updated.routing.key}")
    private String fileUpdatedRoutingKet;

    @Value("${rabbitmq.file.deleted.routing.key}")
    private String fileDeletedRoutingKet;

    public void createFileEvent(FileEntity fileEntity, CurrentUser currentUser) {
        FileEventSend fileEventSend = fileEventMapper.map(fileEntity, currentUser, ActionType.CREATE);
        rabbitTemplate.convertAndSend(fileExchange, fileCreatedRoutingKet, fileEventSend);
    }

    public void updateFileEvent(FileEntity fileEntity, CurrentUser currentUser) {
        FileEventSend fileEventSend = fileEventMapper.map(fileEntity, currentUser, ActionType.UPDATE);
        rabbitTemplate.convertAndSend(fileExchange, fileUpdatedRoutingKet, fileEventSend);
    }

    public void deleteFileEvent(FileEntity fileEntity, CurrentUser currentUser) {
        FileEventSend fileEventSend = fileEventMapper.map(fileEntity, currentUser, ActionType.DELETE);
        rabbitTemplate.convertAndSend(fileExchange, fileDeletedRoutingKet, fileEventSend);
    }
}

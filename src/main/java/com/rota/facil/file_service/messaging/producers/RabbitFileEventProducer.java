package com.rota.facil.file_service.messaging.producers;

import com.rota.facil.file_service.domain.enums.FileAuditAction;
import com.rota.facil.file_service.domain.enums.ResourceName;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.messaging.dto.send.FileCreatedEventSend;
import com.rota.facil.file_service.messaging.dto.send.FileDeletedEventSend;
import com.rota.facil.file_service.messaging.dto.send.FileUpdatedEventSend;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitFileEventProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.file.exchange}")
    private String fileExchange;

    @Value("${rabbitmq.file.created.routing.key}")
    private String fileCreatedRoutingKet;

    @Value("${rabbitmq.file.updated.routing.key}")
    private String fileUpdatedRoutingKet;

    @Value("${rabbitmq.file.deleted.routing.key}")
    private String fileDeletedRoutingKet;

    public void createFileEvent(FileEntity fileEntity, CurrentUser currentUser) {
        FileAuditAction auditAction = FileAuditAction.FILE_CREATED;
        FileCreatedEventSend eventSend = new FileCreatedEventSend(
                currentUser.userId(),
                currentUser.prefectureId(),
                currentUser.role(),
                currentUser.email(),
                auditAction.title(currentUser.email(), fileEntity.getOriginalFilename()),
                auditAction.getActionType(),
                ResourceName.FILE.name(),
                fileEntity.getId(),
                fileEntity.getId(),
                fileEntity.getOwnerId(),
                fileEntity.getOriginalFilename()
        );

        rabbitTemplate.convertAndSend(fileExchange, fileCreatedRoutingKet, eventSend);
    }

    public void updateFileEvent(FileEntity fileEntity, CurrentUser currentUser) {
        FileAuditAction auditAction = FileAuditAction.FILE_UPDATED;
        FileUpdatedEventSend eventSend = new FileUpdatedEventSend(
                currentUser.userId(),
                currentUser.prefectureId(),
                currentUser.role(),
                currentUser.email(),
                auditAction.title(currentUser.email(), fileEntity.getOriginalFilename()),
                auditAction.getActionType(),
                ResourceName.FILE.name(),
                fileEntity.getId(),
                fileEntity.getId(),
                fileEntity.getOwnerId(),
                fileEntity.getOriginalFilename()
        );

        rabbitTemplate.convertAndSend(fileExchange, fileUpdatedRoutingKet, eventSend);
    }

    public void deleteFileEvent(FileEntity fileEntity, CurrentUser currentUser) {
        FileAuditAction auditAction = FileAuditAction.FILE_DELETED;
        FileDeletedEventSend eventSend = new FileDeletedEventSend(
                currentUser.userId(),
                currentUser.prefectureId(),
                currentUser.role(),
                currentUser.email(),
                auditAction.title(currentUser.email(), fileEntity.getOriginalFilename()),
                auditAction.getActionType(),
                ResourceName.FILE.name(),
                fileEntity.getId(),
                fileEntity.getId(),
                fileEntity.getOwnerId(),
                fileEntity.getOriginalFilename()
        );

        rabbitTemplate.convertAndSend(fileExchange, fileDeletedRoutingKet, eventSend);
    }
}

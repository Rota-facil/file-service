package com.rota.facil.file_service.messaging.consumers;

import com.rota.facil.file_service.business.FileService;
import com.rota.facil.file_service.domain.enums.Role;
import com.rota.facil.file_service.messaging.dto.receive.AuthDeletePrefectureEventReceive;
import com.rota.facil.file_service.messaging.dto.receive.AuthDeleteUserEventReceive;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitAuthEventConsumer {
    private final FileService fileService;

    @RabbitListener(queues = "${rabbitmq.file.user.deleted.queue}")
    public void handlerUserDeleted(AuthDeleteUserEventReceive authDeleteUserEventReceive) {
        if (authDeleteUserEventReceive.role().equals(Role.STUDENT.name()) || authDeleteUserEventReceive.role().equals(Role.DRIVER.name())) fileService.deleteAllByOwner(authDeleteUserEventReceive.userId());
    }

    @RabbitListener(queues = "${rabbitmq.file.prefecture.deleted.queue}")
    public void handlerPrefectureDeleted(AuthDeletePrefectureEventReceive authDeletePrefectureEventReceive) {
        fileService.deleteAllByOwner(authDeletePrefectureEventReceive.prefectureId());
    }

}

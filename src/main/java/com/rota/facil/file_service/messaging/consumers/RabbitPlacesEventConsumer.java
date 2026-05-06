package com.rota.facil.file_service.messaging.consumers;

import com.rota.facil.file_service.business.FileService;
import com.rota.facil.file_service.messaging.dto.receive.PlacesDeleteBoardEventReceive;
import com.rota.facil.file_service.messaging.dto.receive.PlacesDeleteInstitutionEventReceive;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitPlacesEventConsumer {
    private final FileService fileService;

    @RabbitListener(queues = "${rabbitmq.file.institution.deleted.queue}")
    public void handlerInstitutionDeleted(PlacesDeleteInstitutionEventReceive placesDeleteInstitutionEventReceive) {
        fileService.deleteAllByOwner(placesDeleteInstitutionEventReceive.institutionId());
    }

    @RabbitListener(queues = "${rabbitmq.file.boarding.deleted.queue}")
    public void handlerBoardingDeleted(PlacesDeleteBoardEventReceive placesDeleteBoardEventReceive) {
        fileService.deleteAllByOwner(placesDeleteBoardEventReceive.boardId());
    }
}

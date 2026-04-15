package com.rota.facil.file_service.messaging.consumers;

import com.rota.facil.file_service.messaging.dto.receive.PlacesDeleteBoardEventReceive;
import com.rota.facil.file_service.messaging.dto.receive.PlacesDeleteInstitutionEventReceive;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitPlacesEventConsumer {
    // injeta um fileService para fazer as operacoes de lógica de negócio

    @RabbitListener(queues = "${rabbitmq.file.institution.deleted.queue}")
    public void handlerInstitutionDeleted(PlacesDeleteInstitutionEventReceive placesDeleteInstitutionEventReceive) {
        // chamar um service para deletar os arquivos da instituição
    }

    @RabbitListener(queues = "${rabbitmq.file.boarding.deleted.queue}")
    public void handlerBoardingDeleted(PlacesDeleteBoardEventReceive placesDeleteBoardEventReceive) {
        // chamar um service para deletar os arquivos de pontos de paradas
    }
}

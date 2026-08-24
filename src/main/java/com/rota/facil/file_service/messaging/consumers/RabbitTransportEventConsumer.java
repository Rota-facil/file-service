package com.rota.facil.file_service.messaging.consumers;

import com.rota.facil.file_service.business.files.DeleteAllFileByOwnerUseCase;
import com.rota.facil.file_service.messaging.dto.receive.TransportDeleteBusEventReceive;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitTransportEventConsumer {
    private final DeleteAllFileByOwnerUseCase deleteAllFileByOwnerUseCase;

    @RabbitListener(queues = "${rabbitmq.file.bus.deleted.queue}")
    public void handlerBusDeleted(TransportDeleteBusEventReceive transportDeleteBusEventReceive) {
        deleteAllFileByOwnerUseCase.execute(transportDeleteBusEventReceive.busId());
    }
}

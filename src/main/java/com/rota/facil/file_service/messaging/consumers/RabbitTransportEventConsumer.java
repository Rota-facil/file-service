package com.rota.facil.file_service.messaging.consumers;

import com.rota.facil.file_service.messaging.dto.receive.DeleteBusEventReceive;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitTransportEventConsumer {
    // injeta um fileService para fazer as operacoes de lógica de negócio

    @RabbitListener(queues = "${rabbitmq.file.bus.deleted.queue}")
    public void handlerBusDeleted(DeleteBusEventReceive deleteBusEventReceive) {
        // chamar um service para deletar os arquivos de onibus
    }
}

package com.rota.facil.file_service.messaging.consumers;

import com.rota.facil.file_service.messaging.dto.receive.AuthDeletePrefectureEventReceive;
import com.rota.facil.file_service.messaging.dto.receive.AuthDeleteUserEventReceive;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitAuthEventConsumer {
    // injeta um fileService para fazer as operacoes de lógica de negócio

    @RabbitListener(queues = "${rabbitmq.file.user.deleted.queue}")
    public void handlerUserDeleted(AuthDeleteUserEventReceive authDeleteUserEventReceive) {
        // chamar um service para deletar os arquivos do usuario
    }

    @RabbitListener(queues = "${rabbitmq.file.prefecture.deleted.queue}")
    public void handlerPrefectureDeleted(AuthDeletePrefectureEventReceive authDeletePrefectureEventReceive) {
        // chamar um service para deletar os arquivos da prefeitura
    }

}

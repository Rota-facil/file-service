package com.rota.facil.file_service.messaging.dto.receive;

import java.util.UUID;

public record DeletePrefectureEventReceive(
        UUID prefectureId
) {
}

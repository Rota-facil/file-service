package com.rota.facil.file_service.messaging.dto.send;

import java.util.UUID;

public record FileUpdatedEventSend(
        UUID userId,
        String role,
        String userEmail,
        String actionTitle,
        String actionType,
        String resourceName,
        UUID resourceId,
        UUID fileId,
        UUID ownerId,
        String originalFilename
) {
}

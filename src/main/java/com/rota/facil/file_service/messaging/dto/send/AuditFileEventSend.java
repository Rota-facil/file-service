package com.rota.facil.file_service.messaging.dto.send;

import com.rota.facil.file_service.domain.enums.ActionType;
import com.rota.facil.file_service.domain.enums.ResourceName;
import com.rota.facil.file_service.domain.enums.Role;

import java.util.UUID;

public record AuditFileEventSend(
        UUID userId,
        Role role,
        String actionTitle,
        ActionType actionType,
        ResourceName resourceName,
        UUID resourceId
) {
}

package com.rota.facil.file_service.http.dto.request;

import java.util.UUID;

public record CurrentUser(
        UUID userId,
        UUID prefectureId,
        String email,
        String role
) {
}

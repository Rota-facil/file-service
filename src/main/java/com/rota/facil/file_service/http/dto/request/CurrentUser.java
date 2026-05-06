package com.rota.facil.file_service.http.dto.request;

import com.rota.facil.file_service.domain.enums.Role;

import java.util.UUID;

public record CurrentUser(
        UUID userId,
        UUID prefectureId,
        String email,
        String role
) {
    public boolean isNotAdmin() {
        return !role.equals(Role.ADMIN.name());
    }
}

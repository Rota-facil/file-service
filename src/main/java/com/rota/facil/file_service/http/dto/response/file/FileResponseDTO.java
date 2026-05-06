package com.rota.facil.file_service.http.dto.response.file;

import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.domain.enums.OwnerType;

import java.util.UUID;

public record FileResponseDTO(
        UUID id,
        String originalFilename,
        String presignedUrl,
        FileCategory fileCategory,
        OwnerType ownerType
) {
}

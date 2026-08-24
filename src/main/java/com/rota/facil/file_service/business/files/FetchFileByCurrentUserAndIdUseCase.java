package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.business.helpers.files.FetchFileByCreatorHelper;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import com.rota.facil.file_service.persistence.mappers.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FetchFileByCurrentUserAndIdUseCase {
    private final FetchFileByCreatorHelper fetchFileByCreatorHelper;
    private final FileMapper fileMapper;

    public FileResponseDTO execute(CurrentUser currentUser, UUID fileId) {
        return fileMapper.map(fetchFileByCreatorHelper.execute(fileId, currentUser.userId()));
    }
}

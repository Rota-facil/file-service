package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.domain.exceptions.FileNotFoundException;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import com.rota.facil.file_service.persistence.mappers.FileMapper;
import com.rota.facil.file_service.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchProfilePicUseCase {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public FileResponseDTO execute(CurrentUser currentUser) {
        FileEntity fileFound = fileRepository.findProfileByCreatorIdAndCategory(currentUser.userId())
                .orElseThrow(FileNotFoundException::new);
        return fileMapper.map(fileFound);
    }
}

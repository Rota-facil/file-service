package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import com.rota.facil.file_service.persistence.mappers.FileMapper;
import com.rota.facil.file_service.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListFileByOwnerAndCategoryUseCase {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public List<FileResponseDTO> execute(UUID ownerId, FileCategory fileCategory) {
        return fileRepository.findAllByOwnerIdAndCategory(ownerId, fileCategory)
                .stream()
                .map(fileMapper::map)
                .toList();
    }
}

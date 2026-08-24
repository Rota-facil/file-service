package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import com.rota.facil.file_service.persistence.mappers.FileMapper;
import com.rota.facil.file_service.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListFileByCategory {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public List<FileResponseDTO> execute(FileCategory fileCategory) {
        return fileRepository.findAllByCategory(fileCategory)
                .stream()
                .map(fileMapper::map)
                .toList();
    }
}

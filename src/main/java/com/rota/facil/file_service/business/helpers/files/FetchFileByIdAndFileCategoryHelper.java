package com.rota.facil.file_service.business.helpers.files;

import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.domain.exceptions.FileNotFoundException;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import com.rota.facil.file_service.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FetchFileByIdAndFileCategoryHelper {
    private final FileRepository fileRepository;

    public FileEntity execute(UUID fileId, FileCategory fileCategory) {
        return fileRepository.findByIdAndCategory(fileId, fileCategory)
                .orElseThrow(FileNotFoundException::new);
    }
}

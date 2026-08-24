package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.business.helpers.files.FetchFileByIdAndFileCategoryHelper;
import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import com.rota.facil.file_service.persistence.mappers.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FetchFileByIdAndCategoryUseCase {
    private final FileMapper fileMapper;
    private final FetchFileByIdAndFileCategoryHelper fetchFileByIdAndFileCategoryHelper;

    public FileResponseDTO execute(UUID fileId, FileCategory fileCategory) {
        return fileMapper.map(fetchFileByIdAndFileCategoryHelper.execute(fileId, fileCategory));
    }
}

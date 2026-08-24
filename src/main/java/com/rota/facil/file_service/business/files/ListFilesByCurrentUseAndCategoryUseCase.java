package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import com.rota.facil.file_service.persistence.mappers.FileMapper;
import com.rota.facil.file_service.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListFilesByCurrentUseAndCategoryUseCase {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public List<FileResponseDTO> execute(CurrentUser currentUser, FileCategory fileCategory) {
        if (currentUser.isNotAdmin()) {
            return fileRepository.findAllByCreatorIdAndCategory(currentUser.userId(), fileCategory)
                    .stream()
                    .map(fileMapper::map)
                    .toList();
        } else {
            return fileRepository.findAllByPrefectureIdAndCategory(currentUser.prefectureId(), fileCategory)
                    .stream()
                    .map(fileMapper::map)
                    .toList();
        }
    }
}

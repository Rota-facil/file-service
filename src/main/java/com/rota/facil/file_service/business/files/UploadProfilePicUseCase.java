package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.buckets.minio.business.MinioService;
import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.domain.enums.OwnerType;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import com.rota.facil.file_service.persistence.mappers.FileMapper;
import com.rota.facil.file_service.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadProfilePicUseCase {
    private final FileRepository fileRepository;
    private final MinioService minioService;
    private final FileMapper fileMapper;

    @Transactional
    public FileResponseDTO execute(CurrentUser currentUser, MultipartFile multipartFile) {
        FileEntity preSaved = fileRepository.findProfileByCreatorIdAndCategory(currentUser.userId())
                .map(
                        file -> {
                            minioService.delete(file.getFileUrl());
                            file.update(multipartFile);
                            return file;
                        }
                )
                .orElseGet(
                        () -> FileEntity.fetchNewFile(multipartFile, currentUser, currentUser.userId(), currentUser.prefectureId(), FileCategory.PROFILE_PIC, OwnerType.USER)
                );

        FileEntity saved = fileRepository.save(preSaved);
        minioService.upload(multipartFile, saved.getFileUrl());

        return fileMapper.map(saved);
    }
}

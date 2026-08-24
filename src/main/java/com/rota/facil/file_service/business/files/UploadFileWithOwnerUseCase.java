package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.buckets.minio.business.MinioService;
import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import com.rota.facil.file_service.persistence.mappers.FileMapper;
import com.rota.facil.file_service.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileWithOwnerUseCase {
    private final FileRepository fileRepository;
    private final MinioService minioService;
    private final FileMapper fileMapper;

    @Transactional
    public FileResponseDTO execute(CurrentUser currentUser, UUID ownerId, MultipartFile multipartFile, FileCategory fileCategory) {
        FileEntity saved = fileRepository.save(FileEntity.fetchNewFile(multipartFile, currentUser, ownerId, currentUser.prefectureId(), fileCategory, fileCategory.getOwnerType()));
        minioService.upload(multipartFile, saved.getFileUrl());
        return fileMapper.map(saved);
    }
}

package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.buckets.minio.business.MinioService;
import com.rota.facil.file_service.business.helpers.files.FetchFileByCreatorHelper;
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
public class UpdateFileUseCase {
    private final MinioService minioService;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final FetchFileByCreatorHelper fetchFileByCreatorHelper;

    @Transactional
    public FileResponseDTO execute(CurrentUser currentUser, UUID fileId, MultipartFile multipartFile) {
        FileEntity fileFound = fetchFileByCreatorHelper.execute(currentUser.userId(), fileId);

        minioService.delete(fileFound.getFileUrl());
        fileFound.update(multipartFile);

        minioService.upload(multipartFile, fileFound.getFileUrl());

        return fileMapper.map(fileRepository.save(fileFound));
    }
}

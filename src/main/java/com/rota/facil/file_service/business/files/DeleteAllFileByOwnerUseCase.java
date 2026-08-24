package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.buckets.minio.business.MinioService;
import com.rota.facil.file_service.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteAllFileByOwnerUseCase {
    private final FileRepository fileRepository;
    private final MinioService minioService;

    @Transactional
    public void execute(UUID ownerId) {
        List<String> fileUrls = fileRepository.findAllFileUrlByOwnerId(ownerId);
        minioService.deleteAll(fileUrls);
        fileRepository.deleteAllByOwnerId(ownerId);
    }
}

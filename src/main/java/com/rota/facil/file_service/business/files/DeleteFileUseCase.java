package com.rota.facil.file_service.business.files;

import com.rota.facil.file_service.buckets.minio.business.MinioService;
import com.rota.facil.file_service.business.helpers.files.FetchFileByCreatorHelper;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import com.rota.facil.file_service.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteFileUseCase {
    private final FetchFileByCreatorHelper fetchFileByCreatorHelper;
    private final FileRepository fileRepository;
    private final MinioService minioService;

    public void execute(CurrentUser currentUser, UUID fileId) {
        FileEntity fileFound = fetchFileByCreatorHelper.execute(fileId, currentUser.userId());
        minioService.delete(fileFound.getFileUrl());
        fileRepository.delete(fileFound);
    }
}

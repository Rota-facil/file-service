package com.rota.facil.file_service.business;

import com.rota.facil.file_service.buckets.minio.business.MinioService;
import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.domain.enums.OwnerType;
import com.rota.facil.file_service.domain.exceptions.FileNotFoundException;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import com.rota.facil.file_service.persistence.mappers.FileMapper;
import com.rota.facil.file_service.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final MinioService minioService;

    @Transactional
    public FileResponseDTO uploadProfilePic(CurrentUser currentUser, MultipartFile multipartFile) {
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

    @Transactional
    public FileResponseDTO upload(CurrentUser currentUser, MultipartFile multipartFile, FileCategory fileCategory) {
        FileEntity saved = fileRepository.save(FileEntity.fetchNewFile(multipartFile, currentUser, currentUser.userId(), currentUser.prefectureId(), fileCategory, fileCategory.getOwnerType()));
        minioService.upload(multipartFile, saved.getFileUrl());
        return fileMapper.map(saved);
    }

    @Transactional
    public FileResponseDTO update(CurrentUser currentUser, UUID fileId, MultipartFile multipartFile) {
        FileEntity fileFound = this.fetchEntity(currentUser, fileId);

        minioService.delete(fileFound.getFileUrl());
        fileFound.update(multipartFile);

        minioService.upload(multipartFile, fileFound.getFileUrl());

        return fileMapper.map(fileRepository.save(fileFound));
    }

    public FileResponseDTO fetchProfile(CurrentUser currentUser) {
        FileEntity fileFound = fileRepository.findProfileByCreatorIdAndCategory(currentUser.userId())
                .orElseThrow(FileNotFoundException::new);
        return fileMapper.map(fileFound);
    }

    public FileResponseDTO fetch(CurrentUser currentUser, UUID fileId) {
        return fileMapper.map(this.fetchEntity(currentUser.userId(), fileId));
    }

    public FileResponseDTO fetchByIdAndCategory(UUID fileId, FileCategory fileCategory) {
        return fileMapper.map(this.fetchEntity(fileId, fileCategory));
    }

    public List<FileResponseDTO> list(CurrentUser currentUser, FileCategory fileCategory) {
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

    public List<FileResponseDTO> listByOwnerIdAndCategory(UUID ownerId, FileCategory fileCategory) {
        return fileRepository.findAllByOwnerIdAndCategory(ownerId, fileCategory)
                .stream()
                .map(fileMapper::map)
                .toList();
    }

    public List<FileResponseDTO> listByCategory(FileCategory fileCategory) {
        return fileRepository.findAllByCategory(fileCategory)
                .stream()
                .map(fileMapper::map)
                .toList();
    }

    public void delete(CurrentUser currentUser, UUID fileId) {
        FileEntity fileFound = this.fetchEntity(currentUser, fileId);
        minioService.delete(fileFound.getFileUrl());
        fileRepository.delete(fileFound);
    }

    @Transactional
    public void deleteAllByOwner(UUID ownerId) {
        List<String> fileUrls = fileRepository.findAllFileUrlByOwnerId(ownerId);
        minioService.deleteAll(fileUrls);
        fileRepository.deleteAllByOwnerId(ownerId);
    }

    private FileEntity fetchEntity(CurrentUser currentUser, UUID fileId) {
        if (currentUser.isNotAdmin()) return fileRepository.findByIdAndCreatorId(fileId, currentUser.userId()).orElseThrow(FileNotFoundException::new);

        else return fileRepository.findByIdAndPrefectureId(fileId, currentUser.prefectureId()).orElseThrow(FileNotFoundException::new);
    }

    private FileEntity fetchEntity(UUID creatorId, UUID fileId) {
        return fileRepository.findByIdAndCreatorId(fileId, creatorId).orElseThrow(FileNotFoundException::new);
    }

    private FileEntity fetchEntity(UUID fileId) {
        return fileRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
    }

    private FileEntity fetchEntity(UUID fileId, FileCategory fileCategory) {
        return fileRepository.findByIdAndCategory(fileId, fileCategory)
                .orElseThrow(FileNotFoundException::new);
    }
}

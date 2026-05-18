package com.rota.facil.file_service.persistence.entities;

import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.domain.enums.OwnerType;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Table(name = "files_tb")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "file_id")
    private UUID id;

    @Column(name = "original_filename")
    private String originalFilename;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name = "creator_id")
    private UUID creatorId;

    @Column(name = "prefecture_id")
    private UUID prefectureId;

    @Column(name = "owner_type")
    @Enumerated(EnumType.STRING)
    private OwnerType ownerType;

    @Column(name = "file_category")
    @Enumerated(EnumType.STRING)
    private FileCategory fileCategory;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size_bytes")
    private Double fileSizeBytes;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public void update(MultipartFile multipartFile) {
        this.originalFilename = multipartFile.getOriginalFilename();
        this.mimeType = multipartFile.getContentType();
        this.fileSizeBytes = (double) multipartFile.getSize();
        this.fileUrl = this.generateFileUrl();
    }

    public String generateFileUrl() {
        return this.ownerType.getPath() + "/" + ownerId + this.fileCategory.getPath() + "/" + LocalDateTime.now() + "_" + originalFilename;
    }

    public static FileEntity fetchNewFile(MultipartFile multipartFile, CurrentUser currentUser, UUID ownerId, UUID prefectureId, FileCategory fileCategory, OwnerType ownerType) {
        FileEntity fileCreated = FileEntity.builder()
                .originalFilename(multipartFile.getOriginalFilename())
                .prefectureId(prefectureId)
                .creatorId(currentUser.userId())
                .ownerId(ownerId)
                .ownerType(ownerType)
                .mimeType(multipartFile.getContentType())
                .fileSizeBytes((double) multipartFile.getSize())
                .fileCategory(fileCategory)
                .build();
        fileCreated.setFileUrl(fileCreated.generateFileUrl());
        return fileCreated;
    }
}

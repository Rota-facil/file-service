package com.rota.facil.file_service.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "owner_type")
    private String ownerType;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size_bytes")
    private Double fileSizeBytes;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

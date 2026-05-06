package com.rota.facil.file_service.persistence.repositories;

import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {
    @Query("""
        SELECT f FROM FileEntity f
        WHERE f.creatorId = :creatorId
        AND f.fileCategory IN (com.rota.facil.file_service.domain.enums.FileCategory.PROFILE_PIC)
    """)
    Optional<FileEntity> findProfileByCreatorIdAndCategory(@Param("creatorId") UUID creatorId);

    @Query("""
        SELECT f FROM FileEntity f
        WHERE f.creatorId = :creatorId
        AND f.fileCategory = :fileCategory
    """)
    List<FileEntity> findAllByCreatorIdAndCategory(@Param("creatorId") UUID creatorId, @Param("fileCategory") FileCategory fileCategory);

    @Query("""
        SELECT f FROM FileEntity f
        WHERE f.id = :fileId
        AND f.creatorId = :creatorId
    """)
    Optional<FileEntity> findByIdAndCreatorId(@Param("fileId") UUID fileId, @Param("creatorId") UUID creatorId);

    Optional<FileEntity> findByIdAndPrefectureId(UUID fileId, UUID prefectureId);

    @Query("""
        SELECT f FROM FileEntity f
        WHERE f.prefectureId = :prefectureId
        AND f.fileCategory = :fileCategory
    """)
    List<FileEntity> findAllByPrefectureIdAndCategory(@Param("prefectureId") UUID prefectureId, @Param("fileCategory") FileCategory fileCategory);

    @Query("""
        SELECT f FROM FileEntity f
        WHERE f.fileCategory = :fileCategory
    """)
    List<FileEntity> findAllByCategory(@Param("fileCategory") FileCategory fileCategory);

    @Query("""
        SELECT f FROM FileEntity f
        WHERE f.ownerId = :ownerId
        AND f.fileCategory = :fileCategory
    """)
    Optional<FileEntity> findAllByOwnerIdAndCategory(@Param("ownerId") UUID ownerId, @Param("fileCategory") FileCategory fileCategory);

    @Query("""
        SELECT f FROM FileEntity f
        WHERE f.id = :fileId
        AND f.fileCategory = :fileCategory
    """)
    Optional<FileEntity> findByIdAndCategory(@Param("fileId") UUID fileId, @Param("fileCategory") FileCategory fileCategory);

    @Query("""
        SELECT f.fileUrl FROM FileEntity f
        WHERE f.ownerId = :ownerId
    """)
    List<String> findAllFileUrlByOwnerId(@Param("ownerId") UUID ownerId);

    @Modifying
    @Query("""
        DELETE FROM FileEntity f
        WHERE f.ownerId = :ownerId
    """)
    void deleteAllByOwnerId(@Param("ownerId") UUID ownerId);
}

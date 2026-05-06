package com.rota.facil.file_service.persistence.mappers;

import com.rota.facil.file_service.buckets.minio.business.MinioService;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = MinioService.class)
public interface FileMapper {
    @Mapping(target = "presignedUrl", source = "fileUrl", qualifiedByName = "getPresignedUrl")
    FileResponseDTO map(FileEntity entity);
}

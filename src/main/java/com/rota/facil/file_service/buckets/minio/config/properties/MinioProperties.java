package com.rota.facil.file_service.buckets.minio.config.properties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class MinioProperties {
    private final String bucketName;
}

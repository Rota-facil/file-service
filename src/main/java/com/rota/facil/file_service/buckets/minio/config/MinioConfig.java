package com.rota.facil.file_service.buckets.minio.config;

import com.rota.facil.file_service.buckets.minio.config.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
public class MinioConfig {
    @Value(value = "${minio.secret.key}")
    private String secretKey;

    @Value(value = "${minio.access.key}")
    private String accessKey;

    @Value(value = "${minio.url}")
    private String minioUrl;

    @Value(value = "${minio.bucket.name}")
    private String bucketName;

    @Bean
    @Primary
    public MinioClient minioClient() throws Exception {
         MinioClient minioClient = MinioClient.builder()
                .credentials(accessKey, secretKey)
                .endpoint(minioUrl)
                .build();


         if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
             minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
             log.debug("bucket {} successfully created", bucketName);
         } else {
             log.debug("bucket {} already exists", bucketName);
         }

         return minioClient;
    }

    @Bean
    public MinioProperties minioProperties() {
        return MinioProperties.builder()
                .bucketName(bucketName)
                .build();
    }
}

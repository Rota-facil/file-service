package com.rota.facil.file_service.buckets.minio.business;

import com.rota.facil.file_service.buckets.minio.config.properties.MinioProperties;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioProperties minioProperties;
    private final MinioClient minioClient;

    @Named("upload")
    public void upload(MultipartFile multipartFile, String path) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(path)
                            .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Named("getPresignedUrl")
    public String getPresignedUrl(String path) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .expiry(1, TimeUnit.DAYS)
                            .object(path)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Named("delete")
    public void delete(String path) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(path)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll(List<String> fileUrls) {
        if (fileUrls.isEmpty()) return;

        try {
            Iterable<Result<DeleteError>> deleteErrors = minioClient.removeObjects(
                    RemoveObjectsArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .objects(fileUrls.stream().map(DeleteObject::new).toList())
                            .build()
            );


            for (Result<DeleteError> result : deleteErrors) {
                result.get();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

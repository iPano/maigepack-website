package com.maigepack.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "app.upload.provider", havingValue = "r2")
public class R2StorageService implements MediaStorageService {

    private final S3Client s3;
    private final String bucket;
    private final String publicBaseUrl;

    public R2StorageService(
            @Value("${app.upload.r2.accountId}") String accountId,
            @Value("${app.upload.r2.accessKeyId}") String accessKeyId,
            @Value("${app.upload.r2.secretAccessKey}") String secretAccessKey,
            @Value("${app.upload.r2.bucket}") String bucket,
            @Value("${app.upload.r2.publicBaseUrl}") String publicBaseUrl) {

        this.bucket = bucket;
        this.publicBaseUrl = publicBaseUrl.replaceAll("/$", ""); // strip trailing slash

        this.s3 = S3Client.builder()
                .endpointOverride(URI.create("https://" + accountId + ".r2.cloudflarestorage.com"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .region(Region.of("auto"))
                .build();
    }

    @Override
    public StoredMedia store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            String original = file.getOriginalFilename() == null ? "upload" : file.getOriginalFilename();
            String safeOriginal = original.replaceAll("[^a-zA-Z0-9._-]", "_");
            String ossKey = "products/" + UUID.randomUUID() + "-" + safeOriginal;
            String mimeType = file.getContentType() == null ? "application/octet-stream" : file.getContentType();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(ossKey)
                    .contentType(mimeType)
                    .contentLength(file.getSize())
                    .build();

            s3.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String url = publicBaseUrl + "/" + ossKey;
            return new StoredMedia(ossKey, url, mimeType, file.getSize());

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to R2", e);
        }
    }
}

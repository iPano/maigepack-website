package com.maigepack.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "app.upload.provider", havingValue = "local", matchIfMissing = true)
public class LocalMediaStorageService implements MediaStorageService {
    private final Path uploadDir;

    public LocalMediaStorageService(@Value("${app.upload.localDir:./uploads}") String uploadLocalDir) {
        this.uploadDir = Paths.get(uploadLocalDir).toAbsolutePath().normalize();
    }

    @Override
    public StoredMedia store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            Files.createDirectories(uploadDir);

            String original = file.getOriginalFilename() == null ? "upload" : file.getOriginalFilename();
            String safeOriginal = original.replaceAll("[^a-zA-Z0-9._-]", "_");
            String ossKey = UUID.randomUUID() + "-" + safeOriginal;

            Path target = uploadDir.resolve(ossKey);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            String mimeType = file.getContentType() == null ? "application/octet-stream" : file.getContentType();
            long size = file.getSize();

            // In production, we'd map this URL to a CDN + OSS bucket.
            String url = "/uploads/" + ossKey;

            return new StoredMedia(ossKey, url, mimeType, size);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store upload", e);
        }
    }
}


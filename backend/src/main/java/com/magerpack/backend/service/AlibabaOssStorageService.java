package com.magerpack.backend.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@ConditionalOnProperty(name = "app.upload.provider", havingValue = "alibaba")
public class AlibabaOssStorageService implements MediaStorageService {
    @Override
    public StoredMedia store(MultipartFile file) {
        // Intentionally not implemented yet.
        // When you provide Alibaba OSS credentials + endpoint/bucket, replace this with:
        // 1) Generate object key
        // 2) Upload stream to OSS (prefer server-side streaming to avoid large temp files)
        // 3) Return object key + public URL
        throw new UnsupportedOperationException("Alibaba OSS upload is not implemented yet. Set UPLOAD_PROVIDER=local for now.");
    }
}


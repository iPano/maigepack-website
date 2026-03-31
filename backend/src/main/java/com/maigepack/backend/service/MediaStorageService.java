package com.maigepack.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface MediaStorageService {
    StoredMedia store(MultipartFile file);
}


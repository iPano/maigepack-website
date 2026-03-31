package com.maigepack.backend.api.admin;

import com.maigepack.backend.model.MediaObject;
import com.maigepack.backend.repository.MediaObjectRepository;
import com.maigepack.backend.service.MediaStorageService;
import com.maigepack.backend.service.StoredMedia;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class AdminUploadsController {
    private final MediaObjectRepository mediaObjectRepository;
    private final MediaStorageService mediaStorageService;

    public AdminUploadsController(MediaObjectRepository mediaObjectRepository,
                                   MediaStorageService mediaStorageService) {
        this.mediaObjectRepository = mediaObjectRepository;
        this.mediaStorageService = mediaStorageService;
    }

    @PostMapping("/uploads")
    public ResponseEntity<UploadResponse> upload(@RequestParam("file") MultipartFile file) {
        StoredMedia stored = mediaStorageService.store(file);

        MediaObject obj = new MediaObject();
        obj.setOssKey(stored.ossKey());
        obj.setUrl(stored.url());
        obj.setMimeType(stored.mimeType());
        obj.setSize(stored.size());

        mediaObjectRepository.save(obj);

        return ResponseEntity.ok(new UploadResponse(stored.url(), stored.mimeType(), stored.size()));
    }
}


package com.maigepack.backend.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "media_object")
public class MediaObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oss_key", nullable = false, length = 256)
    private String ossKey;

    @Column(nullable = false, length = 512)
    private String url;

    @Column(name = "mime_type", nullable = false, length = 128)
    private String mimeType;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getOssKey() {
        return ossKey;
    }

    public void setOssKey(String ossKey) {
        this.ossKey = ossKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}


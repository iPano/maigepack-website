package com.maigepack.backend.api.admin;

public class UploadResponse {
    private final String url;
    private final String mimeType;
    private final long size;

    public UploadResponse(String url, String mimeType, long size) {
        this.url = url;
        this.mimeType = mimeType;
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public long getSize() {
        return size;
    }
}


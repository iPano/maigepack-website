package com.magerpack.backend.service;

public record StoredMedia(String ossKey, String url, String mimeType, long size) {
}


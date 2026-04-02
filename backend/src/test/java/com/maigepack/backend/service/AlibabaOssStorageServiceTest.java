package com.maigepack.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AlibabaOssStorageServiceTest {

    private final AlibabaOssStorageService service = new AlibabaOssStorageService();

    @Test
    void store_alwaysThrowsUnsupportedOperationException() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "image.png", "image/png", "bytes".getBytes());

        assertThatThrownBy(() -> service.store(file))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("Alibaba OSS");
    }

    @Test
    void store_withNullFile_stillThrowsUnsupportedOperationException() {
        assertThatThrownBy(() -> service.store(null))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}

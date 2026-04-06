package com.magerpack.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

class LocalMediaStorageServiceTest {

    @TempDir
    Path tempDir;

    private LocalMediaStorageService service() {
        return new LocalMediaStorageService(tempDir.toString());
    }

    // -----------------------------------------------------------------------
    // Happy path — store a valid file
    // -----------------------------------------------------------------------

    @Test
    void store_validFile_returnsStoredMedia() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.jpg", "image/jpeg", "fake image bytes".getBytes());

        StoredMedia result = service().store(file);

        assertThat(result).isNotNull();
        assertThat(result.url()).startsWith("/uploads/");
        assertThat(result.mimeType()).isEqualTo("image/jpeg");
        assertThat(result.size()).isEqualTo("fake image bytes".length());
        assertThat(result.ossKey()).isNotBlank();
    }

    @Test
    void store_validFile_writesFileToDisk() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "doc.pdf", "application/pdf", "pdf content".getBytes());

        StoredMedia result = service().store(file);

        String filename = result.ossKey();
        Path written = tempDir.resolve(filename);
        assertThat(Files.exists(written)).isTrue();
        assertThat(Files.readAllBytes(written)).isEqualTo("pdf content".getBytes());
    }

    @Test
    void store_validFile_urlMatchesOssKey() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "image.png", "image/png", new byte[]{1, 2, 3});

        StoredMedia result = service().store(file);

        assertThat(result.url()).isEqualTo("/uploads/" + result.ossKey());
    }

    @Test
    void store_validFile_ossKeyContainsSanitisedOriginalName() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "my photo.jpg", "image/jpeg", "bytes".getBytes());

        StoredMedia result = service().store(file);

        // spaces are replaced with underscores in the sanitised original name
        assertThat(result.ossKey()).contains("my_photo.jpg");
    }

    @Test
    void store_validFile_ossKeyContainsUuidPrefix() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "hello".getBytes());

        StoredMedia result = service().store(file);

        // UUID is 36 characters; key format is "<uuid>-<name>"
        String ossKey = result.ossKey();
        assertThat(ossKey.length()).isGreaterThan(36);
        // UUID part separated by dash
        String uuidPart = ossKey.substring(0, 36);
        assertThatCode(() -> java.util.UUID.fromString(uuidPart)).doesNotThrowAnyException();
    }

    // -----------------------------------------------------------------------
    // Null content type — defaults to application/octet-stream
    // -----------------------------------------------------------------------

    @Test
    void store_nullContentType_defaultsToOctetStream() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "binary.bin", null, "data".getBytes());

        StoredMedia result = service().store(file);

        assertThat(result.mimeType()).isEqualTo("application/octet-stream");
    }

    // -----------------------------------------------------------------------
    // Null / empty original filename
    // -----------------------------------------------------------------------

    @Test
    void store_nullOriginalFilename_producesValidOssKey() {
        // MockMultipartFile returns "" (empty string) for a null original filename,
        // so getOriginalFilename() is not null — the "upload" fallback is not triggered.
        // The resulting ossKey should still be a valid, non-blank value.
        MockMultipartFile file = new MockMultipartFile("file", (String) null, "text/plain", "data".getBytes());

        StoredMedia result = service().store(file);

        assertThat(result.ossKey()).isNotBlank();
        assertThat(result.url()).startsWith("/uploads/");
    }

    // -----------------------------------------------------------------------
    // Error cases
    // -----------------------------------------------------------------------

    @Test
    void store_nullFile_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> service().store(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("empty");
    }

    @Test
    void store_emptyFile_throwsIllegalArgumentException() {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "file", "empty.txt", "text/plain", new byte[0]);

        assertThatThrownBy(() -> service().store(emptyFile))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("empty");
    }

    // -----------------------------------------------------------------------
    // Upload directory is created automatically
    // -----------------------------------------------------------------------

    @Test
    void store_uploadDirDoesNotExist_createsDirectoryAutomatically() throws Exception {
        Path nested = tempDir.resolve("a/b/c");
        LocalMediaStorageService svc = new LocalMediaStorageService(nested.toString());

        MockMultipartFile file = new MockMultipartFile(
                "file", "hello.txt", "text/plain", "world".getBytes());
        StoredMedia result = svc.store(file);

        assertThat(Files.exists(nested)).isTrue();
        assertThat(result).isNotNull();
    }
}

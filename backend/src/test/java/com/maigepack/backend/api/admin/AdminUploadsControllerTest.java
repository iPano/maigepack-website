package com.maigepack.backend.api.admin;

import com.maigepack.backend.model.MediaObject;
import com.maigepack.backend.repository.MediaObjectRepository;
import com.maigepack.backend.service.MediaStorageService;
import com.maigepack.backend.service.StoredMedia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUploadsControllerTest {

    @Mock
    private MediaObjectRepository mediaObjectRepository;
    @Mock
    private MediaStorageService mediaStorageService;

    private AdminUploadsController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminUploadsController(mediaObjectRepository, mediaStorageService);
    }

    // -----------------------------------------------------------------------
    // Successful upload
    // -----------------------------------------------------------------------

    @Test
    void upload_successfulStore_returns200() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "img.png", "image/png", new byte[]{1, 2, 3});
        StoredMedia stored = new StoredMedia("key-123", "/uploads/key-123", "image/png", 3L);
        when(mediaStorageService.store(file)).thenReturn(stored);

        ResponseEntity<UploadResponse> response = controller.upload(file);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void upload_successfulStore_responseContainsCorrectFields() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "img.png", "image/png", new byte[]{1, 2, 3});
        StoredMedia stored = new StoredMedia("key-abc", "/uploads/key-abc", "image/png", 3L);
        when(mediaStorageService.store(file)).thenReturn(stored);

        ResponseEntity<UploadResponse> response = controller.upload(file);

        UploadResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getUrl()).isEqualTo("/uploads/key-abc");
        assertThat(body.getMimeType()).isEqualTo("image/png");
        assertThat(body.getSize()).isEqualTo(3L);
    }

    @Test
    void upload_successfulStore_savesMediaObjectToRepository() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "doc.pdf", "application/pdf", "content".getBytes());
        StoredMedia stored = new StoredMedia("key-pdf", "/uploads/key-pdf", "application/pdf", 7L);
        when(mediaStorageService.store(file)).thenReturn(stored);

        controller.upload(file);

        ArgumentCaptor<MediaObject> captor = ArgumentCaptor.forClass(MediaObject.class);
        verify(mediaObjectRepository).save(captor.capture());
        MediaObject saved = captor.getValue();
        assertThat(saved.getOssKey()).isEqualTo("key-pdf");
        assertThat(saved.getUrl()).isEqualTo("/uploads/key-pdf");
        assertThat(saved.getMimeType()).isEqualTo("application/pdf");
        assertThat(saved.getSize()).isEqualTo(7L);
    }

    @Test
    void upload_successfulStore_delegatesToStorageService() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "x.txt", "text/plain", "hi".getBytes());
        StoredMedia stored = new StoredMedia("k", "/uploads/k", "text/plain", 2L);
        when(mediaStorageService.store(file)).thenReturn(stored);

        controller.upload(file);

        verify(mediaStorageService).store(file);
    }

    // -----------------------------------------------------------------------
    // Storage service throws — controller propagates exception
    // -----------------------------------------------------------------------

    @Test
    void upload_storageThrows_exceptionPropagates() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "empty.txt", "text/plain", new byte[0]);
        when(mediaStorageService.store(file)).thenThrow(new IllegalArgumentException("File is empty"));

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> controller.upload(file))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("empty");

        verify(mediaObjectRepository, never()).save(any());
    }
}

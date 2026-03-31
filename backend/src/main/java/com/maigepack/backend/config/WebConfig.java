package com.maigepack.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final Path localDir;

    public WebConfig(@Value("${app.upload.localDir:./uploads}") String localDir) {
        this.localDir = Path.of(localDir).toAbsolutePath().normalize();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve locally uploaded files at /uploads/** for dev/testing.
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(localDir.toUri().toString());
    }
}


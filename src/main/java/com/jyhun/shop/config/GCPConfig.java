package com.jyhun.shop.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class GCPConfig {

    @Value("${spring.cloud.gcp.credentials.location}")
    private String credentialsPath;

    @Bean
    public Storage storage() throws IOException {
        InputStream keyFile = ResourceUtils.getURL(credentialsPath).openStream();
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(keyFile);
        return StorageOptions.newBuilder().setCredentials(googleCredentials).build().getService();
    }

}

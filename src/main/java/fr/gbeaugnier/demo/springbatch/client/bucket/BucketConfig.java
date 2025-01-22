package fr.gbeaugnier.demo.springbatch.client.bucket;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
public class BucketConfig {

    @Value("${minio.client.endpoint}")
    private String endpoint;

    @Value("${minio.client.accessKey}")
    private String accessKey;

    @Value("${minio.client.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient generateMinioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

}

package fr.gbeaugnier.demo.springbatch.client.bucket;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class BucketClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MinioClient minioClient;

    public String get_csv(String bucketName, String recordKey, String objectVersion) {

        try {

            InputStream in =minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(recordKey)
                            .versionId(objectVersion)
                            .build()
            );

            final File tempFile = File.createTempFile("stream2file", ".csv");
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(in, out);
            }

            return tempFile.getAbsolutePath();

        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}

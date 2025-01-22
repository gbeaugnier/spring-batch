package fr.gbeaugnier.demo.springbatch.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlUtils {

    public static String getSQLFileAsString(String path) {
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            return new String(FileCopyUtils.copyToByteArray(classPathResource.getInputStream()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}

package org.rangiffler.utils;

import jakarta.xml.bind.DatatypeConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageUtils {

    public static String getDataURI(String imagePath) {

        File file = FileResourcesUtils.getFileFromResource(imagePath);

        String contentType;
        try {
            contentType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] data;
        try {
            data = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String base64str = DatatypeConverter.printBase64Binary(data);

        StringBuilder sb = new StringBuilder();
        sb.append("data:");
        sb.append(contentType);
        sb.append(";base64,");
        sb.append(base64str);

        return sb.toString();
    }
}

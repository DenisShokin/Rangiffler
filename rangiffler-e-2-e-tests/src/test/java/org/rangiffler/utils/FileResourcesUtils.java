package org.rangiffler.utils;

import java.io.File;
import java.util.Objects;

public class FileResourcesUtils {
    private static final ClassLoader cl = FileResourcesUtils.class.getClassLoader();

    public static File getFileFromResource(String fileName) {
        return new File(Objects.requireNonNull(cl.getResource(fileName)).getFile());
    }

}

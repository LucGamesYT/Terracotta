package org.terracotta.util.io;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
@NoArgsConstructor
public class FileCreationUtil {

    @Getter
    private static final List<String> createdFiles = new ArrayList<>();
    private static final String workerPath = System.getProperty("user.dir");

    /**
     * Creates all essential directories for a Terracotta server
     */
    @SneakyThrows
    public static void createDirectories() {
        final List<File> directories = new ArrayList<>();
        final String[] directoryNames = new String[]{"logs", "players", "plugins", "resource_packs", "worlds"};

        Arrays.stream(directoryNames).forEach(directoryName -> directories.add(new File(FileCreationUtil.workerPath, directoryName)));

        for (final File directory : directories) {
            if (!directory.exists()) {
                directory.mkdir();

                if (!FileCreationUtil.createdFiles.contains(directory.getName())) {
                    FileCreationUtil.createdFiles.add(directory.getName());
                }
            }
        }
        directories.clear();
    }

    /**
     * Creates all essential files for a Terracotta server
     */
    @SneakyThrows
    public static void createFiles() {
        final List<File> files = new ArrayList<>();
        final String[] fileNames = new String[]{
                "banned-players-ip.json", "banned-players-name.json", "operators.json",
                "whitelist.json", "server.properties", "terracotta.yml"
        };

        Arrays.stream(fileNames).forEach(fileName -> files.add(new File(FileCreationUtil.workerPath, fileName)));

        for (final File file : files) {
            if (!file.exists()) {
                file.createNewFile();

                if (!FileCreationUtil.createdFiles.contains(file.getName())) {
                    FileCreationUtil.createdFiles.add(file.getName());
                }
            }
        }
        files.clear();
    }
}
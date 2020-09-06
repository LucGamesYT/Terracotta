package org.terracotta.util.io;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.*;
import java.util.*;

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
                "banned-players-ip.json", "banned-players.json", "operators.json",
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

            // writes default data to the current file
            switch (file.getName()) {
                case "server.properties":
                    final Properties properties = new Properties();
                    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                    properties.load(bufferedReader);

                    int notNullProperties = 0;

                    for (final String property : new String[]{"ip", "port", "motd", "maxPlayers", "defaultGameMode"}) {
                        if (properties.getProperty(property) != null) {
                            notNullProperties++;
                        }
                    }

                    if (notNullProperties == 5) {
                        bufferedReader.close();
                        return;
                    }

                    try (final FileWriter fileWriter = new FileWriter(file)) {
                        FileDataManager.createDefaultData(ServerFile.PROPERTIES_SERVER);

                        final StringBuilder contentsBuilder = new StringBuilder();

                        for (final Map.Entry entry : FileDataManager.getEntriesFromFile(ServerFile.PROPERTIES_SERVER)) {
                            contentsBuilder.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
                        }

                        fileWriter.write(contentsBuilder.toString());
                        fileWriter.flush();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        files.clear();
    }
}
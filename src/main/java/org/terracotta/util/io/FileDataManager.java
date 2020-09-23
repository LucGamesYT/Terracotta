package org.terracotta.util.io;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
public class FileDataManager {

    private static final Map<String, Object> serverPropertiesMap = new LinkedHashMap<>();

    /**
     * Creates default data for the given file
     *
     * @param serverFile which is needed to locate the file
     */
    public static void createDefaultData(final ServerFile serverFile) {
        switch (serverFile) {
            case PROPERTIES_SERVER:
                if (FileDataManager.serverPropertiesMap.isEmpty()) {
                    FileDataManager.serverPropertiesMap.put("ip", "127.0.0.1");
                    FileDataManager.serverPropertiesMap.put("port", 19132);
                    FileDataManager.serverPropertiesMap.put("motd", "Terracotta Server v1.0.0");
                    FileDataManager.serverPropertiesMap.put("maxPlayers", 50);
                    FileDataManager.serverPropertiesMap.put("defaultGameMode", 0);
                    FileDataManager.serverPropertiesMap.put("language", "eng");
                }
                break;
        }
    }

    /**
     * Retrieves a {@link Set} of Entries from the {@link Map} which is located by the file
     *
     * @param serverFile which is needed to locate the file
     *
     * @return a fresh {@link Set} of Entries
     */
    public static Set<Map.Entry<String, Object>> getEntriesFromFile(final ServerFile serverFile) {
        switch (serverFile) {
            case PROPERTIES_SERVER:
                return FileDataManager.serverPropertiesMap.entrySet();
        }
        return Collections.emptySet();
    }

    /**
     * Retrieves a properties value from the given file with given key
     *
     * @param serverFile which is needed to retrieve the value
     * @param name       which is the key which is bound to the value
     *
     * @return a fresh {@link Object}
     */
    @SneakyThrows
    public static Object getProperty(final ServerFile serverFile, final String name) {
        final File file = new File(System.getProperty("user.dir"), serverFile.getName());

        if (!file.exists()) {
            return null;
        }

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        if (bufferedReader.lines() == null) {
            return null;
        }

        final Properties properties = new Properties();

        properties.load(bufferedReader);

        bufferedReader.close();

        return properties.getProperty(name);
    }
}
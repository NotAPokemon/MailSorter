package dev.korgi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Map;
import java.io.File;

public class JsonFile {
    private String url;

    public JsonFile(String url) {
        this.url = url;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> read() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(url)));
            return objectMapper.readValue(jsonContent, Map.class);
        } catch (IOException e) {
            return createDefaultJsonFile(objectMapper);
        }
    }

    public String getUrl() {
        return url;
    }

    private Map<String, Object> createDefaultJsonFile(ObjectMapper objectMapper) {
        Map<String, Object> defaultData = Map.of("Default", "INBOX");
        try {
            // Create the JSON file with default content
            objectMapper.writeValue(new File(url), defaultData);
            return defaultData; // Return the default data after creating the file
        } catch (IOException e) {
            e.printStackTrace(); // Log the error
            return null; // Return null in case of failure
        }
    }
}

package com.mycompany.quickchat.part1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON Storage Attribution:
 * Google, 2024. Gson User Guide.
 * https://github.com/google/gson/blob/main/UserGuide.md
 */
public class MessageStorage {
    private final String filePath = "messages.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Save new message to file
    public void saveMessage(Message message) {
        List<Message> existing = loadMessages();
        existing.add(message);
        
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(existing, writer);
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }

    // Read existing messages from file
    public List<Message> loadMessages() {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Message>>() {}.getType();
            List<Message> messages = gson.fromJson(reader, listType);
            return messages != null ? messages : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
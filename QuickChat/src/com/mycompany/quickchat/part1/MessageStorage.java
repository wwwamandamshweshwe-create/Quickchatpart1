package com.mycompany.quickchat.part1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Attribution:
 * Google, 2024. Gson User Guide.
 * https://github.com/google/gson/blob/main/UserGuide.md
 */
public class MessageStorage {
    private final String filePath = "messages.json";
    
    // Gson is the library we researched to easily convert Java objects to JSON text
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Reads existing messages, adds the new one, and saves back to the JSON file
    public void saveMessage(Message message) {
        List<Message> existing = loadMessages();
        existing.add(message);
        
        try (Writer writer = new FileWriter(filePath)) {
            // Converts the list of messages into JSON and writes it to the file
            gson.toJson(existing, writer);
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }

    // Reads the JSON file and converts it back into a List of Message objects
    public List<Message> loadMessages() {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Message>>() {}.getType();
            List<Message> messages = gson.fromJson(reader, listType);
            return messages != null ? messages : new ArrayList<>();
        } catch (IOException e) {
            // If the file doesn't exist yet, just return an empty list
            return new ArrayList<>();
        }
    }
}
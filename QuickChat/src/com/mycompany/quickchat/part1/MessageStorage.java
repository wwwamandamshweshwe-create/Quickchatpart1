package com.mycompany.quickchat.part1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// gson handles the json work
// json attribution: google gson user guide
public class MessageStorage {
    private final String filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // keep sent and disregard in memory only
    // stored messages go to json file
    private final List<Message> sentMessages = new ArrayList<>();
    private final List<Message> disregardedMessages = new ArrayList<>();

    // default file name
    public MessageStorage() {
        this("messages.json");
    }

    // tests can pass their own file so they don't clash
    public MessageStorage(String filePath) {
        this.filePath = filePath;
    }

    // route the message based on its flag
    public void addMessage(Message message) {
        String flag = message.getFlag();
        if ("Sent".equals(flag)) {
            sentMessages.add(message);
        } else if ("Disregard".equals(flag)) {
            disregardedMessages.add(message);
        } else if ("Stored".equals(flag)) {
            saveMessage(message);
        }
    }

    // append to json file
    public void saveMessage(Message message) {
        List<Message> existing = loadMessages();
        existing.add(message);
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(existing, writer);
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }

    // read all stored messages from file
    public List<Message> loadMessages() {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Message>>() {}.getType();
            List<Message> messages = gson.fromJson(reader, listType);
            return messages != null ? messages : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<Message> getSentMessages()        { return sentMessages; }
    public List<Message> getDisregardedMessages() { return disregardedMessages; }
    public List<Message> getStoredMessages()      { return loadMessages(); }

    // build hash array from everything
    public String[] getMessageHashes() {
        List<Message> all = getAllMessages();
        String[] hashes = new String[all.size()];
        for (int i = 0; i < all.size(); i++) hashes[i] = all.get(i).getHash();
        return hashes;
    }

    // build id array from everything
    public String[] getMessageIds() {
        List<Message> all = getAllMessages();
        String[] ids = new String[all.size()];
        for (int i = 0; i < all.size(); i++) ids[i] = all.get(i).getMessageId();
        return ids;
    }

    // all three sources together
    private List<Message> getAllMessages() {
        List<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(disregardedMessages);
        all.addAll(loadMessages());
        return all;
    }

    // sub-menu option 1 - sender and recipient list
    public String generateSenderRecipientList() {
        List<Message> stored = loadMessages();
        if (stored.isEmpty()) return "No stored messages.";
        StringBuilder sb = new StringBuilder();
        for (Message m : stored) {
            String s = (m.getSender() == null || m.getSender().isEmpty()) ? "Unknown" : m.getSender();
            sb.append("Sender: ").append(s)
              .append("  ->  Recipient: ").append(m.getRecipient()).append("\n");
        }
        return sb.toString();
    }

    // sub-menu option 2 - longest message, need a simple loop
    public Message findLongestMessage() {
        List<Message> stored = loadMessages();
        if (stored.isEmpty()) return null;
        Message longest = stored.get(0);
        for (Message m : stored) {
            if (m.getText().length() > longest.getText().length()) longest = m;
        }
        return longest;
    }

    // sub-menu option 3 - check all three arrays for the id
    public Message findByMessageId(String id) {
        for (Message m : loadMessages())      if (m.getMessageId().equals(id)) return m;
        for (Message m : sentMessages)        if (m.getMessageId().equals(id)) return m;
        for (Message m : disregardedMessages) if (m.getMessageId().equals(id)) return m;
        return null;
    }

    // sub-menu option 4 - stored plus sent for that recipient
    public List<Message> findByRecipient(String recipient) {
        List<Message> results = new ArrayList<>();
        for (Message m : loadMessages()) if (m.getRecipient().equals(recipient)) results.add(m);
        for (Message m : sentMessages)   if (m.getRecipient().equals(recipient)) results.add(m);
        return results;
    }

    // sub-menu option 5 - remove stored message with matching hash
    public String deleteByHash(String hash) {
        List<Message> stored = loadMessages();
        Message toDelete = null;
        for (Message m : stored) {
            if (m.getHash().equals(hash)) { toDelete = m; break; }
        }
        if (toDelete == null) return "No stored message found with that hash.";

        stored.remove(toDelete);
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(stored, writer);
        } catch (IOException e) {
            return "Error deleting: " + e.getMessage();
        }
        return "Message: \"" + toDelete.getText() + "\" successfully deleted.";
    }

    // sub-menu option 6 - full report of stored messages
    public String generateReport() {
        List<Message> stored = loadMessages();
        if (stored.isEmpty()) return "No stored messages to report.";
        StringBuilder sb = new StringBuilder("=== STORED MESSAGE REPORT ===\n");
        for (Message m : stored) {
            sb.append("Hash: ").append(m.getHash()).append("\n");
            sb.append("Recipient: ").append(m.getRecipient()).append("\n");
            sb.append("Message: ").append(m.getText()).append("\n---\n");
        }
        return sb.toString();
    }

    // wipe file - handy for tests
    public void clearStorage() {
        try (Writer writer = new FileWriter(filePath)) {
            writer.write("[]");
        } catch (IOException e) {
            System.out.println("Error clearing: " + e.getMessage());
        }
    }
}

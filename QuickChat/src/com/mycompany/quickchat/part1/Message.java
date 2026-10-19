package com.mycompany.quickchat.part1;

import java.util.Random;

public class Message {
    private String messageId;
    private int messageNum;
    private String recipient;
    private String text;
    private String hash;
    private String flag;

    // Generate random 10-digit ID
    public Message(int messageNum, String recipient, String text) {
        this.messageNum = messageNum;
        this.recipient = recipient;
        this.text = text;
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        this.messageId = String.valueOf(id);
        this.hash = buildHash();
        this.flag = "Pending";
    }

    // Force specific ID for testing
    public Message(int messageNum, String recipient, String text, String forcedId) {
        this.messageNum = messageNum;
        this.recipient = recipient;
        this.text = text;
        this.messageId = forcedId;
        this.hash = buildHash();
        this.flag = "Pending";
    }

    // Create Hash: First 2 digits + Loop + First & Last words
    private String buildHash() {
        if (messageId == null || messageId.length() < 2) return "";
        String prefix = messageId.substring(0, 2);
        
        String[] words = text.trim().split("\\s+");
        String first = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
        String last = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        
        return String.format("%s:%d:%s%s", prefix, messageNum, first, last);
    }

    // Check ID length
    public boolean checkMessageId() {
        return messageId != null && messageId.length() == 10;
    }

    // Check recipient format
    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches("^\\+[0-9]{10,12}$");
    }

    // Check message length
    public boolean checkMessageLength() {
        return text != null && !text.isEmpty() && text.length() <= 250;
    }

    public void setFlag(String flag) { this.flag = flag; }

    // Format details for output
    public String getDetails() {
        return "Message ID: " + messageId + "\n" +
               "Message Hash: " + hash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + text;
    }

    public String getHash() { return hash; }
}
package com.mycompany.quickchat.part1;

import java.util.Random;

public class Message {
    private String messageId;
    private int messageNum;
    private String recipient;
    private String text;
    private String hash;
    private String flag;

    // Constructor automatically generates a random 10-digit ID and the Message Hash
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

    // Overloaded constructor used by the JUnit tests to force a specific ID
    public Message(int messageNum, String recipient, String text, String forcedId) {
        this.messageNum = messageNum;
        this.recipient = recipient;
        this.text = text;
        this.messageId = forcedId;
        this.hash = buildHash();
        this.flag = "Pending";
    }

    // Rubric Rule: Hash is first 2 digits of ID + ":" + loop count + ":" + FIRSTWORDLASTWORD
    private String buildHash() {
        if (messageId == null || messageId.length() < 2) return "";
        String prefix = messageId.substring(0, 2);
        
        String[] words = text.trim().split("\\s+");
        // Remove punctuation and make uppercase
        String first = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
        String last = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        
        // Use String.format to create the "XX:Y:FIRSTLAST" structure
        return String.format("%s:%d:%s%s", prefix, messageNum, first, last);
    }

    // Rubric Rule: Message ID must be 10 digits
    public boolean checkMessageId() {
        return messageId != null && messageId.length() == 10;
    }

    // Rubric Rule: Recipient must start with + and have 10 to 12 digits
    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches("^\\+[0-9]{10,12}$");
    }

    // Rubric Rule: Message must not exceed 250 characters and cannot be empty
    public boolean checkMessageLength() {
        return text != null && !text.isEmpty() && text.length() <= 250;
    }

    // Sets the status (Sent, Stored, or Disregard)
    public void setFlag(String flag) { 
        this.flag = flag; 
    }

    // Returns the formatted details in the exact order required by the brief
    public String getDetails() {
        return "Message ID: " + messageId + "\n" +
               "Message Hash: " + hash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + text;
    }

    public String getHash() { return hash; }
}
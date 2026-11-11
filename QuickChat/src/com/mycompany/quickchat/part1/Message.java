package com.mycompany.quickchat.part1;

import java.util.Random;

public class Message {
    private String messageId;
    private int messageNum;
    private String sender;
    private String recipient;
    private String text;
    private String hash;
    private String flag;

    // normal one - random 10 digit id
    public Message(int messageNum, String recipient, String text) {
        this.messageNum = messageNum;
        this.recipient = recipient;
        this.text = text;
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        this.messageId = String.valueOf(id);
        this.hash = buildHash();
        this.flag = "Pending";
        this.sender = "";
    }

    // for tests - force the id so the hash stays predictable
    public Message(int messageNum, String recipient, String text, String forcedId) {
        this.messageNum = messageNum;
        this.recipient = recipient;
        this.text = text;
        this.messageId = forcedId;
        this.hash = buildHash();
        this.flag = "Pending";
        this.sender = "";
    }

    // hash = first 2 of id : num : FIRSTWORD + LASTWORD (all caps)
    // need to strip punctuation before uppercasing
    private String buildHash() {
        if (messageId == null || messageId.length() < 2) return "";
        String prefix = messageId.substring(0, 2);
        String[] words = text.trim().split("\\s+");
        String first = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
        String last = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        return String.format("%s:%d:%s%s", prefix, messageNum, first, last);
    }

    // id must be exactly 10 digits
    public boolean checkMessageId() {
        return messageId != null && messageId.length() == 10;
    }

    // needs + country code then digits
    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches("^\\+[0-9]{10,12}$");
    }

    // max 250 chars, can't be empty
    public boolean checkMessageLength() {
        return text != null && !text.isEmpty() && text.length() <= 250;
    }

    // "Sent" or "Stored" or "Disregard"
    public void setFlag(String flag) { this.flag = flag; }

    // who sent it - used by reports
    public void setSender(String sender) { this.sender = sender; }

    public String getMessageId() { return messageId; }
    public String getSender()    { return sender; }
    public String getRecipient() { return recipient; }
    public String getText()      { return text; }
    public String getHash()      { return hash; }
    public String getFlag()      { return flag; }
    public int getMessageNum()   { return messageNum; }

    // display order: id, hash, recipient, message
    public String getDetails() {
        return "Message ID: " + messageId + "\n" +
               "Message Hash: " + hash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + text;
    }
}

package com.mycompany.quickchat.part1;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    // --- Recipient Tests ---
    @Test
    public void testValidMessageRecipient() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertTrue(msg.checkRecipientCell());
    }

    @Test
    public void testInvalidMessageRecipient() {
        Message msg = new Message(0, "08575975889", "Hi");
        assertFalse(msg.checkRecipientCell());
    }

    // --- Message Length Tests ---
    @Test
    public void testValidMessageLength() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageLength());
    }

    @Test
    public void testMessageTooLong() {
        StringBuilder sb = new StringBuilder();
        // Create a message with 260 characters to test the 250 limit
        for (int i = 0; i < 260; i++) sb.append("a");
        Message msg = new Message(0, "+27718693002", sb.toString());
        assertFalse(msg.checkMessageLength());
    }

    // --- Message Hash Tests ---
    @Test
    public void testOfficialMessageHash() {
        // Force ID to "0012345678" to exactly match the assignment test data
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678");
        assertEquals("00:0:HITONIGHT", msg.getHash());
    }
    
    // --- Message ID Tests ---
    @Test
    public void testMessageIDLength() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertTrue(msg.checkMessageId());
    }
}
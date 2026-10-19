package com.mycompany.quickchat.part1;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    // Test: Valid Message Length (Success)
    @Test public void testValidMessageLength() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageLength());
    }

    // Test: Message Too Long (Failure)
    @Test public void testMessageTooLong() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) sb.append("a");
        Message msg = new Message(0, "+27718693002", sb.toString());
        assertFalse(msg.checkMessageLength());
    }

    // Test: Valid Recipient (Success)
    @Test public void testValidMessageRecipient() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertTrue(msg.checkRecipientCell());
    }

    // Test: Invalid Recipient (Failure)
    @Test public void testInvalidMessageRecipient() {
        Message msg = new Message(0, "08575975889", "Hi");
        assertFalse(msg.checkRecipientCell());
    }

    // Test: Exact Official Hash
    @Test public void testOfficialMessageHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678");
        assertEquals("00:0:HITONIGHT", msg.getHash());
    }
    
    // Test: Remainder of hashes in a loop (Required by brief)
    @Test public void testMessageHashesInLoop() {
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight?", 
            "Hi Keegan, did you receive the payment?", 
            "Yohoooo, I am at your gate."
        };
        String[] expectedHashes = {
            "00:0:HITONIGHT", 
            "00:1:HIPAYMENT", 
            "00:2:YOHOOOOGATE" 
        };
        
        // Loop through test data as requested by the assignment
        for (int i = 0; i < messages.length; i++) {
            Message msg = new Message(i, "+27718693002", messages[i], "0012345678");
            assertEquals(expectedHashes[i], msg.getHash());
        }
    }
}
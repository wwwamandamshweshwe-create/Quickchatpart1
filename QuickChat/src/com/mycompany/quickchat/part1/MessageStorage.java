package com.mycompany.quickchat.part1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class MessageTest {

    // own file so tests don't touch real messages.json
    private MessageStorage storage;

    @Before
    public void setUp() {
        storage = new MessageStorage("test-messages.json");
        storage.clearStorage();
    }

    // part 2 tests

    @Test public void testValidMessageLength() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageLength());
    }

    @Test public void testMessageTooLong() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) sb.append("a");
        Message msg = new Message(0, "+27718693002", sb.toString());
        assertFalse(msg.checkMessageLength());
    }

    @Test public void testValidMessageRecipient() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertTrue(msg.checkRecipientCell());
    }

    @Test public void testInvalidMessageRecipient() {
        Message msg = new Message(0, "08575975889", "Hi");
        assertFalse(msg.checkRecipientCell());
    }

    @Test public void testOfficialMessageHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678");
        assertEquals("00:0:HITONIGHT", msg.getHash());
    }

    // loop through test data as the brief asks
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
        for (int i = 0; i < messages.length; i++) {
            Message msg = new Message(i, "+27718693002", messages[i], "0012345678");
            assertEquals(expectedHashes[i], msg.getHash());
        }
    }

    // part 3 tests

    // sent array should hold message 1 and message 4
    @Test public void testSentMessagesArrayCorrectlyPopulated() {
        Message m1 = new Message(0, "+27834557896", "Did you get the cake?", "1111111111");
        m1.setFlag("Sent"); storage.addMessage(m1);

        Message m2 = new Message(1, "+27838884567", "Where are you? You are late!", "1111111112");
        m2.setFlag("Stored"); storage.addMessage(m2);

        Message m3 = new Message(2, "+27834484567", "Yohoooo, I am at your gate.", "1111111113");
        m3.setFlag("Disregard"); storage.addMessage(m3);

        Message m4 = new Message(3, "Developer", "It is dinner time !", "0838884567");
        m4.setFlag("Sent"); storage.addMessage(m4);

        List<Message> sent = storage.getSentMessages();
        assertEquals(2, sent.size());
        assertEquals("Did you get the cake?", sent.get(0).getText());
        assertEquals("It is dinner time !", sent.get(1).getText());
    }

    // message 2 should be the longest of the stored ones
    @Test public void testDisplayLongestMessage() {
        Message m2 = new Message(1, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "1111111112");
        m2.setFlag("Stored"); storage.addMessage(m2);

        Message m5 = new Message(4, "+27838884567", "Ok, I am leaving without you.", "1111111115");
        m5.setFlag("Stored"); storage.addMessage(m5);

        Message longest = storage.findLongestMessage();
        assertNotNull(longest);
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getText());
    }

    // search by id - test data message 4 has id 0838884567
    @Test public void testSearchByMessageId() {
        Message m4 = new Message(3, "Developer", "It is dinner time !", "0838884567");
        m4.setFlag("Sent"); storage.addMessage(m4);

        Message found = storage.findByMessageId("0838884567");
        assertNotNull(found);
        assertEquals("It is dinner time !", found.getText());
    }

    // search by recipient should give both stored messages for that number
    @Test public void testSearchByRecipient() {
        Message m2 = new Message(1, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "1111111112");
        m2.setFlag("Stored"); storage.addMessage(m2);

        Message m5 = new Message(4, "+27838884567", "Ok, I am leaving without you.", "1111111115");
        m5.setFlag("Stored"); storage.addMessage(m5);

        List<Message> results = storage.findByRecipient("+27838884567");
        assertEquals(2, results.size());
        assertEquals("Where are you? You are late! I have asked you to be on time.", results.get(0).getText());
        assertEquals("Ok, I am leaving without you.", results.get(1).getText());
    }

    // delete by hash - force id so hash is known up front
    @Test public void testDeleteByMessageHash() {
        Message m2 = new Message(1, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "1234567891");
        m2.setFlag("Stored"); storage.addMessage(m2);

        assertEquals(1, storage.getStoredMessages().size());

        String result = storage.deleteByHash(m2.getHash());
        assertTrue(result.contains("successfully deleted"));
        assertEquals(0, storage.getStoredMessages().size());
    }

    // full report must show hash, recipient and message
    @Test public void testDisplayReport() {
        Message m2 = new Message(1, "+27838884567", "Where are you? You are late!", "1234567891");
        m2.setFlag("Stored"); storage.addMessage(m2);

        String report = storage.generateReport();
        assertTrue(report.contains(m2.getHash()));
        assertTrue(report.contains("+27838884567"));
        assertTrue(report.contains("Where are you? You are late!"));
    }

    // hash and id arrays should be filled
    @Test public void testHashAndIdArrays() {
        Message m1 = new Message(0, "+27834557896", "Did you get the cake?", "1111111111");
        m1.setFlag("Sent"); storage.addMessage(m1);

        Message m2 = new Message(1, "+27838884567", "Stored text", "2222222222");
        m2.setFlag("Stored"); storage.addMessage(m2);

        String[] hashes = storage.getMessageHashes();
        String[] ids = storage.getMessageIds();
        assertEquals(2, hashes.length);
        assertEquals(2, ids.length);
    }
}

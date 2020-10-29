package Modules.Entities;

import java.time.LocalDateTime;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {

    // Test Message constructor
    @Test
    public void testMessage() {
        LocalDateTime messageDate = LocalDateTime.of(2020, 10, 15, 13, 25);
        Message m = new Message("Hello World!", "user1", "user2", messageDate);
        Message m2 = new Message("So long and thanks for all the fish", "user1", "user2");
    }

    // Test Message getters
    @Test
    public void testGetters() {
        LocalDateTime messageDate = LocalDateTime.of(2020, 10, 15, 13, 25);
        Message m = new Message("Hello World!", "user1", "user2", messageDate);
        Message m2 = new Message("So long and thanks for all the fish", "user3", "user1");
        assertEquals("Hello World!", m.getContent());
        assertEquals("So long and thanks for all the fish", m2.getContent());
        assertEquals("user1", m.getSender());
        assertEquals("user1", m2.getReceiver());
        assertEquals("user2", m.getReceiver());
        assertEquals("user3", m2.getSender());
        assertEquals(messageDate, m.getDateTime());
        assertNotNull(m2.getDateTime());
    }
}
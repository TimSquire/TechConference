package Modules.UseCases;

import Modules.Entities.Attendee;
import Modules.Entities.Message;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MessageManagerTest {
    LocalDateTime message1Date = LocalDateTime.of(2020, 10, 15, 13, 25);
    LocalDateTime message2Date = LocalDateTime.of(2020, 10, 15, 13, 26);
    LocalDateTime message3Date = LocalDateTime.of(2020, 10, 15, 13, 35);
    String message1ID = String.format("%s,%s,%s", "user1", "user2", message1Date);
    String message2ID = String.format("%s,%s,%s", "user2", "user3", message2Date);
    String message3ID = String.format("%s,%s,%s", "user3", "user1", message3Date);
    Message message1 = new Message("yo event 2 is lit!", "user1", "user2", message1ID, message1Date);
    Message message2 = new Message("where is the bathroom?", "user2", "user3", message2ID, message2Date);
    Message message3 = new Message("how you doin?", "user3", "user1", message3ID, message3Date);

    // Test MessageManager Constructor
    @Test
    public void testMessageManager() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        MessageManager manager = new MessageManager(messages);
    }

    // Test getUserMessages
    @Test
    public void testGetUserMessages() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        MessageManager manager = new MessageManager(messages);
        ArrayList<Message> userMessages = manager.getUserMessages("user1");
        assertEquals(message1, userMessages.get(0));
        assertEquals(message3, userMessages.get(1));
    }

    // Test sendMessage
    @Test
    public void testSendMessage() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        MessageManager manager = new MessageManager(messages);

        ArrayList<Message> user1Messages = manager.getUserMessages("user1");
        ArrayList<Message> user2Messages = manager.getUserMessages("user2");

        assertEquals(2, user1Messages.size());
        assertEquals(2, user2Messages.size());
        manager.sendMessage("user1", "user2", "never gonna give you up");

        user1Messages = manager.getUserMessages("user1");
        user2Messages = manager.getUserMessages("user2");
        assertEquals(3, user1Messages.size());
        assertEquals(3, user2Messages.size());

        assertEquals(user1Messages.get(2), user2Messages.get(2));
    }

    // Test getConversation
    @Test
    public void testGetConversation() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        MessageManager manager = new MessageManager(messages);
        manager.sendMessage("user1", "user2", "never gonna give you up");
        manager.sendMessage("user2", "user1", "never gonna let you down");

        ArrayList<Message> conversation = manager.getConversation("user1", "user2");

        assertEquals("user1", conversation.get(0).getSenderID());
        assertEquals("user1", conversation.get(1).getSenderID());
        assertEquals("user2", conversation.get(2).getSenderID());

        assertEquals("yo event 2 is lit!", conversation.get(0).getContent());
        assertEquals("never gonna give you up", conversation.get(1).getContent());
        assertEquals("never gonna let you down", conversation.get(2).getContent());
    }

    @Test
    public void testSendMessageFirstMessage(){
        ArrayList<Message> newList = new ArrayList<>();
        MessageManager mm = new MessageManager(newList);
        Attendee a1 = new Attendee("Attendee1", "pwd", "101");
        Attendee a2 = new Attendee("Attendee2", "pwd", "202");
        a1.addToFriendList("202");
        a2.addToFriendList("101");
        mm.sendMessage(a1.getID(), a2.getID(), "Hello Attendee 2!");
        assertTrue(mm.getUserMessages(a1.getID()).size() == 1);
        assertTrue(mm.getUserMessages(a2.getID()).size() == 1);
    }

}
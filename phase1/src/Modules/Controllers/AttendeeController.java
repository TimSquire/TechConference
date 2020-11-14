package Modules.Controllers;

import Modules.Entities.Attendee;
import Modules.Entities.Event;
import Modules.Entities.Message;
import Modules.Entities.User;
import Modules.Gateways.UserGateway;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.EventManager;
import Modules.UseCases.MessageManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AttendeeController {
    private AttendeeManager attendeeManager;
    private EventManager eventManager;
    private MessageManager messageManager;
    private String attendeeID;
    public AttendeeController(AttendeeManager attendeeManager, EventManager eventManager,String attendeeID,
                              MessageManager messageManager){
        this.attendeeManager = attendeeManager;
        this.eventManager = eventManager;
        this.attendeeID = attendeeID;
        this.messageManager = messageManager;
    }


    /**
     *
     * @return the list of existing events
     */
    public ArrayList<Event> displayEvents(){
        return eventManager.getEventList();
    }

    /**
     *
     * @param eventName the name of the event that attendee wishes to sign up for
     * @return true if the sign up was successful, false if attendee was not available at that time or if the event was
     * full
     */
    public boolean signUp(String eventName){
        String eventID = eventManager.getEventID(eventName);
        ArrayList<Event> events = eventManager.getEventList();
        boolean signUpSuccessful = false;
        for (Event event: events){
            if (event.getID().equals(eventID)){
                if (attendeeManager.timeAvailable(attendeeManager.getAttendee(attendeeID), event.getStartTime(), event.getEndTime(), eventManager) &&
                        eventManager.canAttend(event.getID())){
                    attendeeManager.addEventToAttendee(attendeeManager.getAttendee(attendeeID), event, eventManager);
                    eventManager.addAttendee(event.getID(), attendeeID);
                    signUpSuccessful = true;
                }
            }
        }
        return signUpSuccessful;
    }

    /**
     *
     * @param receiverID the ID of the user that this message is to be sent to
     * @param message the content of the message to be sent
     * @return true if the message was successfully sent, false if the user was not on attendee's friends list
     */
    public boolean sendMessage(String receiverID, String message){
        if (attendeeManager.getAttendee(attendeeID).getFriendList().contains(receiverID)){
            messageManager.sendMessage(attendeeID, receiverID, message);
            return true;
        }
        return false;
    }

    /**
     *
     * @param userID the ID of the user to be added to attendee's friends list
     * @return true if the given user was added to attendee's friend list, false if the user was already friend
     */
    public boolean addUserToFriendList(String userID){
        if(!attendeeManager.getAttendee(attendeeID).getFriendList().contains(userID)){
            attendeeManager.getAttendee(attendeeID).addToFriendList(userID);
            return true;
        }
        return false;
    }

    /**
     * Returns the message received by user and the full conversation between the receiver and sender
     * @param receiverId the id of the user who received the message
     * @param senderId the id of the user who sends the message
     * @return array list of messages that correspond to the sorted conversation between sender and receiver
     */
    public ArrayList<Message> seeMessage(String receiverId, String senderId){
        return messageManager.getConversation(receiverId, senderId);
    }

}



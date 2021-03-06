package Modules.Controllers;

import Modules.Entities.Attendee;
import Modules.Entities.Message;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.EventManager;
import Modules.UseCases.MessageManager;
import Modules.UseCases.SpeakerManager;

import java.util.ArrayList;

/**
 * SpeakerController class allows speakers to message attendees and also get a list of the events they are hosting
 */
public class SpeakerController {
    private String speakerId;
    private SpeakerManager speakerManager;
    private EventManager eventManager;
    private AttendeeManager attendeeManager;
    private MessageManager messageManager;

    /**
     * A constructor for the speakerController class
     * @param speakerId the Id of a specific speaker
     * @param eventManager the event manager for the conference
     * @param speakerManager the speaker manager for the conference
     * @param attendeeManager the attendee manager for the conference
     * @param messageManager the message manager for the conference
     */
    public SpeakerController(String speakerId, EventManager eventManager, SpeakerManager speakerManager,
                             AttendeeManager attendeeManager, MessageManager messageManager){
        this.speakerId = speakerId;
        this.eventManager = eventManager;
        this.speakerManager = speakerManager;
        this.attendeeManager = attendeeManager;
        this.messageManager = messageManager;
    }

    /**
     * Return a list of eventIDs of events that this speaker(the speaker with id = speakerId) is hosting
     * @return a list of eventIDs of events that this speaker(the speaker with id = speakerId) is hosting
     */
    public ArrayList<String> showEvents(){
        return speakerManager.getSpeaker(speakerId).getHostEvents();
//        ArrayList<Event> allEvents = eventManager.getEventList();
//        ArrayList<Event> myEvents = new ArrayList<>();
//        for(Event event: allEvents){
//            if(speakerManager.getSpeaker(speakerId).getHostEvents().contains(event.getID())){
//                myEvents.add(event);
//            }
//        }
//        return myEvents;
    }

    /**
     * Return a list of Attendees that are attending at least 1 event this speaker is hosting
     * @return a list of Attendees that are attending at least 1 event this speaker is hosting
     */
    public ArrayList<Attendee> getAttendees(){
        ArrayList<Attendee> allAttendees = attendeeManager.getAttendeeList();
        ArrayList<String> myEventIds = speakerManager.getSpeaker(speakerId).getHostEvents();
        ArrayList<Attendee> myAttendees = new ArrayList<>();
        for(Attendee attendee: allAttendees){
            for(String eventId: myEventIds){
                if(attendee.alreadyAttendingEvent(eventId)){
                    myAttendees.add(attendee);
                    break;
                }
            }
        }
        return myAttendees;
    }

    /**
     * Sends a message to all Attendees coming to an event this speaker is hosting
     * @param message the message to be sent
     * @return whether a message was sent or not
     */
    public boolean messageAll(String message){
        if(getAttendees().isEmpty()){
            return false;
        } else{
            for(Attendee attendee: this.getAttendees()){
                messageManager.sendMessage(speakerId, attendee.getID(), message);
            }
            return true;
        }
    }

    /**
     * Sends a message to an individual Attendee
     * @param recipientId the Id of the attendee the message is being sent to
     * @param message the message that is being sent
     * @return whether a message was sent or not
     */
    public boolean message(String recipientId, String message){
        for(Attendee attendee: this.getAttendees()){
            if(attendee.getID().equals(recipientId)){
                messageManager.sendMessage(speakerId, recipientId, message);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all the messages that the speaker specified by the stored speakerID has sent or received
     * @return an ArrayList of all messages that the speaker sent or received
     */
    public ArrayList<Message> getAllMessages(){
        return messageManager.getUserMessages(speakerId);
    }

}

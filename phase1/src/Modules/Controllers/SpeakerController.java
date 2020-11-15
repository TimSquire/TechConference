package Modules.Controllers;

import Modules.Entities.Event;
import Modules.Entities.Speaker;
import Modules.Entities.User;
import Modules.UseCases.EventManager;
import Modules.UseCases.MessageManager;
import Modules.UseCases.SpeakerManager;

import java.util.ArrayList;

public class SpeakerController {
    private String speakerID;
    private SpeakerManager speakerManager;
    private EventManager eventManager;
    private MessageManager messageManager;


    public SpeakerController(String speakerID, EventManager eventManager, SpeakerManager speakerManager){
        this.speakerID = speakerID;
        this.eventManager = eventManager;
        this.speakerManager = speakerManager;
    }
    public void addSpeakers(ArrayList<User> users){
        int ind = 0;
        while(ind < users.size()){
            if(users.get(ind).getID().startsWith("s")) {
                // speakerManager.addSpeaker(users.get(ind).getUsername(), users.get(ind).getUsername(), users.get(ind).getPassword(), users.get(ind).getHostEvents);
            }
        }
    }
}

package modules.controllers;

import static org.junit.Assert.*;

import modules.entities.Speaker;
import modules.gateways.*;
import modules.presenters.AttendeeOptionsPresenter;
import modules.presenters.Model;
import modules.usecases.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

// tests would affect database info if connected to database
// tests pass but since we are not connecting there is an SQLException message that is printed
public class AttendeeControllerTest {

    Model model = new Model();
    // a test class representing the view with the strings that should be passes to UI in variable returnValue
    AttendeeOptionsPresenter attendeeOptionsPresenter = new AttendeeOptionsPresenter(model);
    UpdateInfo updateInfo = new UpdateInfo(new MessageGateway(), new EventGateway(),
                                                new UserGateway(), new RoomGateway());


    @Test
    public void testAttendeeController(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        MessageManager messageManager = new MessageManager(new ArrayList<>());
    }

    @Test
    public void testDisplayEvents(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, organizerManager, speakerManager,
                eventManager, "a1234", messageManager, attendeeOptionsPresenter, updateInfo);
        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 0);
        LocalDateTime time2 = LocalDateTime.of(2020, 1, 1, 2, 0);
        eventManager.createEvent("1", time1, time2, "e1234",2);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("{'eventID': 'e1234', 'name': 'unnamed event', 'startTime': '2020-01-01T01:00'," +
                " 'endTime': '2020-01-01T02:00', 'roomNumber': '1', 'capacity': 2, 'remainingSeats': 2}");
    }

    @Test
    public void testSignUp(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, organizerManager, speakerManager,
                eventManager, "a1234", messageManager, attendeeOptionsPresenter, updateInfo);
        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 0);
        LocalDateTime time2 = LocalDateTime.of(2020, 1, 1, 2, 0);
        eventManager.createEvent("1", time1, time2, "e1234",2);
        eventManager.renameEvent("e1234", "name");
        attendeeController.attendEvent("e1234");
        assertEquals("e1234", attendeeManager.getAttendee("a1234").getEventsList().get(0));
        attendeeController.attendEvent("e1234");
    }

    @Test
    public void testCancelEnrollment(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, organizerManager, speakerManager,
                eventManager, "a1234", messageManager, attendeeOptionsPresenter, updateInfo);
        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 0);
        LocalDateTime time2 = LocalDateTime.of(2020, 1, 1, 2, 0);
        eventManager.createEvent("1", time1, time2, "e1234",2);
        eventManager.renameEvent("e1234", "name");
        eventManager.addAttendee("e1234", "a1234");
        attendeeController.attendEvent("e1234");
        attendeeController.cancelEnrollment("e1234");
        assertEquals(true, attendeeManager.getAttendee("a1234").hasNoEvents());
        attendeeController.cancelEnrollment("e1234");
    }

    @Test
    public void testSendMessage(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());
        ArrayList<String> friendsList = new ArrayList<>();
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        attendeeManager.addAttendee("username2", "password2", "a2345", new ArrayList<>());
        attendeeManager.getAttendee("a1234").addToFriendList("a2345");
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, organizerManager, speakerManager,
                eventManager, "a1234", messageManager, attendeeOptionsPresenter, updateInfo);
        attendeeController.sendMessage("a2345", "heyyyy");
        attendeeController.sendMessage("a4567", "uh oh"); // message not sent user since not on friendList
    }

    @Test
    public void testAddUserToFriendList(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());
        ArrayList<String> friendsList = new ArrayList<>();
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        attendeeManager.addAttendee("username2", "password2", "a2345", new ArrayList<>());
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, organizerManager, speakerManager,
                eventManager, "a1234", messageManager, attendeeOptionsPresenter, updateInfo);
        attendeeController.addUserToFriendList("a2345");
        attendeeController.addUserToFriendList("a2345");
    }
}

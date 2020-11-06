package Modules.UseCases;

import Modules.Entities.Event;
import Modules.Entities.Room;
import Modules.Exceptions.RoomNotFoundException;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoomManagerTest {

    @Test
    public void testCreateRoom(){
        RoomManager roomManager1 = new RoomManager(); // for constructor that takes in room number
        RoomManager roomManager2 = new RoomManager(); // for constructor that auto generates room number


        //should start with no events in room
        assertEquals(new ArrayList<Room>(), roomManager1.getRooms() );

        // creating a room
        roomManager1.createRoom("r0",2);
        roomManager2.createRoom(2);

        ArrayList<Room> expected = new ArrayList<>();
        expected.add(new Room("r0", 2));

        assertTrue(testRoomArrayListEquals(expected,roomManager1.getRooms()));
        assertTrue(testRoomArrayListEquals(expected,roomManager2.getRooms()));

        // creating another room
        roomManager1.createRoom("r1", 5);
        roomManager2.createRoom(5);

        expected.add(new Room("r1", 5));
        assertTrue(testRoomArrayListEquals(expected,roomManager1.getRooms()));
        assertTrue(testRoomArrayListEquals(expected,roomManager2.getRooms()));


    }

    @Test
    // tests relating to accessing events occurring in a room
    public void testRoomEvents(){

        RoomManager roomManager = new RoomManager();

        // adding a room
        roomManager.createRoom("r1",2);
        ArrayList<Room> expected = new ArrayList<>();
        expected.add(new Room("r1", 2));
        assertTrue(testRoomArrayListEquals(expected,roomManager.getRooms()));

        // capacityOfRoom()
        assertEquals(2, roomManager.capacityOfRoom("r1"));

        // isEventInRoom()
        assertFalse(roomManager.isEventInRoom("r1", "e1"));

        // getEventsInRoom()
        // should have no events in room 1
        assertEquals(new ArrayList<String>(), roomManager.getEventsInRoom("r1"));

        // addEventToRoom() - Adding event to room 1
        LocalDateTime date1 = LocalDateTime.of(2020, 11, 3, 11, 11);
        Event event1 = new Event("r1", date1, date1.plusHours(1), "e1");
        roomManager.addEventToRoom("r1","e1");
        ArrayList<String> expected1 = new ArrayList<>();
        expected1.add("e1");
        assertEquals(expected1, roomManager.getEventsInRoom("r1"));

        // removeEventFromRoom()
        roomManager.removeEventFromRoom("r1","e1");
        expected1.remove("e1");
        assertEquals(expected1, roomManager.getEventsInRoom("1"));

    }

    @Test
    public void testIsRoomAvailable(){
        RoomManager roomManager = new RoomManager();
        EventManager eventManager = new EventManager();

        LocalDateTime time1 = LocalDateTime.of(2020,11, 5, 1 ,0);
        LocalDateTime time2 = LocalDateTime.of(2020,11, 5, 2 ,0);
        LocalDateTime time3 = LocalDateTime.of(2020,11, 5, 3 ,0);
        LocalDateTime time4 = LocalDateTime.of(2020,11, 5, 4 ,0);
        LocalDateTime time5 = LocalDateTime.of(2020,11, 5, 5 ,0);

        // adding a room 0 to eventManager
        roomManager.createRoom("r0",2);
        ArrayList<Room> expected = new ArrayList<>();
        expected.add(new Room("r0", 2));
        assertTrue(testRoomArrayListEquals(expected,roomManager.getRooms()));

        // adding events e1 and e2 to eventManager
        // eventManager.createEvent("0",time2);
        // create event doesn't take a id?
        // TODO: finish test


        // room 0 has no events
        assertTrue(roomManager.isRoomAvailable("r0", time1, time3, eventManager));




    }


    // Testing that the correct exceptions are raised if we try to access a room that does not exist in a RoomManager

    @Test(expected = RoomNotFoundException.class)
    public void testCapacityOfRoomEx(){
        RoomManager roomManager = new RoomManager();
        roomManager.capacityOfRoom("r0");
    }

    @Test(expected = RoomNotFoundException.class)
    public void testIsEventInRoomEx(){
        RoomManager roomManager = new RoomManager();
        roomManager.isEventInRoom("r0", "e1");
    }

    @Test(expected = RoomNotFoundException.class)
    public void testAddEventToRoomEx(){
        RoomManager roomManager = new RoomManager();
        roomManager.addEventToRoom("r0", "e1");
    }

    // helper method to determine if two ArrayLists of Rooms have the same rooms
    // using the .equals() method in Room not the one from Object
    private boolean testRoomArrayListEquals(ArrayList<Room> rooms1, ArrayList<Room> rooms2){
        for (Room room1: rooms1){
            boolean isFound = false;
            for (Room room2: rooms2){
                if (room1.equals(room2)){
                    isFound = true;
                }
            }
            if (!isFound){
                return false;
            }
        }
        for (Room room2: rooms2){
            boolean isFound = false;
            for (Room room1: rooms1){
                if (room1.equals(room2)){
                    isFound = true;
                }
            }
            if (!isFound){
                return false;
            }
        }
        return true;
    }


}
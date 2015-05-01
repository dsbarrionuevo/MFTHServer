package mfthserver.map.buildingstrategies;

import java.util.ArrayList;
import mfthserver.map.Map;
import mfthserver.map.room.Room;
import mfthserver.map.tiles.DoorTile;

/**
 *
 * @author Diego
 */
public abstract class MapBuildingStrategy {

    protected int roomsCount;
    protected float tileWidth, tileHeight;
    protected ArrayList<Room> rooms;
    protected Room firstRoom;

    public MapBuildingStrategy(int roomsCount, float tileWidth, float tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.roomsCount = roomsCount;
        this.rooms = new ArrayList<>();
    }

    public abstract void build(Map map);

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Room getRoomById(int roomId) {
        for (Room room : rooms) {
            if (room.getRoomId() == roomId) {
                return room;
            }
        }
        return null;
    }

    public Room getFirstRoom() {
        return firstRoom;
    }

    protected void connectRooms(Room room1, int tileX1, int tileY1, Room room2, int tileX2, int tileY2) {
        DoorTile doorRoom1 = room1.putDoor(tileX1, tileY1);
        DoorTile doorRoom2 = room2.putDoor(tileX2, tileY2);
        doorRoom1.setMyRoom(room1);
        doorRoom1.setConnectedTo(doorRoom2);
        doorRoom2.setMyRoom(room2);
        doorRoom2.setConnectedTo(doorRoom1);
    }
    /*

     public ArrayList<Passage> getPassages() {
     return passages;
     }

     public final  Passage findPassageByRoom(Room target) {
     Passage found = null;
     for (Passage passage : passages) {
     if (passage.getRoom().equals(target)) {
     found = passage;
     }
     }
     return found;
     }

     protected final Passage findPassageByIdRoom(int target) {
     Passage found = null;
     for (Passage passage : passages) {
     if (passage.getRoom().getRoomId() == target) {
     found = passage;
     }
     }
     return found;
     }

     public class Passage {

     private Room room;
     private ArrayList<Room> connections;

     public Passage(Room room, ArrayList<Room> passages) {
     this.room = room;
     this.connections = passages;
     }

     public Passage(Room room, Room connection) {
     this(room);
     this.addConnection(connection);
     }

     public Room getRoom() {
     return room;
     }

     public ArrayList<Room> getConnections() {
     return connections;
     }

     public Passage(Room room) {
     this(room, new ArrayList<Room>());
     }

     public void addConnection(Room connection) {
     this.connections.add(connection);
     }

     }*/

}

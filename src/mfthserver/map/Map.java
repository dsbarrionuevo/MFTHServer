package mfthserver.map;

import java.util.ArrayList;
import mfthserver.common.Placeable;
import mfthserver.map.buildingstrategies.ImprovedFileMapBuildingStrategy;
import mfthserver.map.buildingstrategies.MapBuildingStrategy;
import mfthserver.map.room.Room;
import mfthserver.map.tiles.DoorTile;

/**
 *
 * @author Diego
 */
public class Map {

    private ArrayList<Room> rooms;
    private Room currentRoom;
    private MapBuildingStrategy buildingStrategy;

    public Map() {
        this.buildingStrategy = new ImprovedFileMapBuildingStrategy("res/maps/map1.txt", 50, 50);
        this.buildingStrategy.build(this);
        this.rooms = this.buildingStrategy.getRooms();
    }

    public boolean placeObject(Placeable placeable, int tileX, int tileY) {
        placeable.setRoom(currentRoom);
        return this.currentRoom.placeObject(placeable, tileX, tileY);
    }

    public void nextRoom(Room currentRoom, DoorTile doorTile, Placeable placeable) {
        DoorTile otherDoor = doorTile.getConnectedTo();
        Room nextRoom = otherDoor.getMyRoom();
        //placeObject(placeable, nextRoom.getRoomId(), otherDoor.getTileX(), otherDoor.getTileY());
        currentRoom.removeObject(placeable);
        nextRoom.addObject(placeable, otherDoor.getTileX(), otherDoor.getTileY());
        nextRoom.focusObject(placeable);
        changeRoom(nextRoom.getRoomId());
    }

    public void changeRoom(int idNewRoom) {
        this.currentRoom = buildingStrategy.getRoomById(idNewRoom);
    }

    public Room getRoomForPlayer() {
        Room chosenRoom = null;
        do {
            chosenRoom = rooms.get((int) (Math.random() * (rooms.size() - 1)));
        } while (!chosenRoom.hasTreasure());
        if (chosenRoom == null) {
            chosenRoom = rooms.get(0);
        }
        return chosenRoom;
    }
}

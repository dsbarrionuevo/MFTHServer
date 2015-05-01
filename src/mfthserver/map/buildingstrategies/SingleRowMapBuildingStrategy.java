package mfthserver.map.buildingstrategies;

import java.util.ArrayList;
import mfthserver.map.Map;
import mfthserver.map.room.Room;
import mfthserver.map.room.buildingstrategies.BorderRoomBuildingStrategy;
import mfthserver.map.room.buildingstrategies.DivisionWallRoomBuildingStrategy;
import mfthserver.map.room.buildingstrategies.RoomBuildingStrategy;

/**
 *
 * @author Barrionuevo Diego
 */
public class SingleRowMapBuildingStrategy extends MapBuildingStrategy {

    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;

    private final int orientation;

    public SingleRowMapBuildingStrategy(int orientation, int roomsCount, float tileWidth, float tileHeight) {
        super(roomsCount, tileWidth, tileHeight);
        this.orientation = orientation;
    }

    @Override
    public void build(Map map) {
        this.rooms = new ArrayList<>();
        int widthRoom = 16;
        int heightRoom = 12;
        //create the rooms
        if (this.orientation == ORIENTATION_HORIZONTAL) {
            for (int i = 0; i < roomsCount; i++) {
                widthRoom = (int) (Math.random() * 14) + 4;
                heightRoom = (int) (Math.random() * 8) + 4;
                RoomBuildingStrategy roomBuildingStrategy = new BorderRoomBuildingStrategy(widthRoom, heightRoom, tileWidth, tileHeight);
                Room newRoom = new Room(i, roomBuildingStrategy);
                newRoom.setMap(map);
                rooms.add(newRoom);
            }
        } else if (this.orientation == ORIENTATION_VERTICAL) {
            for (int i = 0; i < roomsCount; i++) {
                widthRoom = (int) (Math.random() * 14) + 4;
                heightRoom = (int) (Math.random() * 8) + 4;
                RoomBuildingStrategy roomBuildingStrategy = new BorderRoomBuildingStrategy(widthRoom, heightRoom, tileWidth, tileHeight);
                Room newRoom = new Room(i, roomBuildingStrategy);
                newRoom.setMap(map);
                rooms.add(newRoom);
            }
        }
        //now create the passages
        for (int i = 0; i < rooms.size(); i++) {
            Room currentRoom = rooms.get(i);
            if (i < rooms.size() - 1) {
                Room nextRoom = rooms.get(i + 1);
                if (this.orientation == ORIENTATION_HORIZONTAL) {
                    int randomPositionCurrentRoom = ((int) (Math.random() * (currentRoom.getRoomHeight() - 2)) + 1);
                    int randomPositionNextRoom = ((int) (Math.random() * (nextRoom.getRoomHeight() - 2)) + 1);
                    connectRooms(currentRoom, currentRoom.getRoomWidth() - 1, randomPositionCurrentRoom, nextRoom, 0, randomPositionNextRoom);
                } else if (this.orientation == ORIENTATION_VERTICAL) {
                    int randomPositionCurrentRoom = ((int) (Math.random() * (currentRoom.getRoomWidth() - 2)) + 1);
                    int randomPositionNextRoom = ((int) (Math.random() * (nextRoom.getRoomWidth() - 2)) + 1);
                    connectRooms(currentRoom, randomPositionCurrentRoom, currentRoom.getRoomHeight() - 1, nextRoom, randomPositionNextRoom, 0);
                }
            }
        }
        this.firstRoom = rooms.get(0);
    }

}

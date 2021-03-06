package mfthserver.map;

import java.util.ArrayList;
import mfthserver.map.buildingstrategies.ImprovedFileMapBuildingStrategy;
import mfthserver.map.buildingstrategies.MapBuildingStrategy;
import mfthserver.map.room.Room;

/**
 *
 * @author Diego
 */
public class Map {

    //should be synchronized
    private ArrayList<Room> rooms;
    private MapBuildingStrategy buildingStrategy;
    //
    private String mapSource;

    public Map(String mapSource) {
        this.mapSource = mapSource;
        this.buildingStrategy = new ImprovedFileMapBuildingStrategy(mapSource, 50, 50);
        this.buildingStrategy.build(this);
        this.rooms = this.buildingStrategy.getRooms();
    }

    public Room getRoomForPlayer() {
        return rooms.get(0);
        /*
         Room chosenRoom = null;
         do {
         chosenRoom = rooms.get((int) (Math.random() * (rooms.size() - 1)));
         } while (!chosenRoom.hasTreasure());
         if (chosenRoom == null) {
         chosenRoom = rooms.get(0);
         }
         return chosenRoom;
         */
    }

    public String getMapSource() {
        return mapSource;
    }

    public Room findRoomById(int roomId) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (room.getRoomId() == roomId) {
                return room;
            }
        }
        return null;
    }

}

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
        Room chosenRoom = null;
        do {
            chosenRoom = rooms.get((int) (Math.random() * (rooms.size() - 1)));
        } while (!chosenRoom.hasTreasure());
        if (chosenRoom == null) {
            chosenRoom = rooms.get(0);
        }
        return chosenRoom;
    }

    public String getMapSource() {
        return mapSource;
    }

}

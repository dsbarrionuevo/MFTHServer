package mfthserver.map.buildingstrategies;

import java.util.ArrayList;
import mfthserver.map.Map;
import mfthserver.map.room.Room;
import mfthserver.map.room.buildingstrategies.ImprovedFileRoomBuildingStrategy;
import mfthserver.map.room.buildingstrategies.RoomBuildingStrategy;
import mfthserver.map.tiles.DoorTile;
import mfthserver.map.tiles.Tile;
import mfthserver.mapreader.MapFile;
import mfthserver.mapreader.MapReader;
import mfthserver.mapreader.RoomFile;

/**
 *
 * @author Barrionuevo Diego
 */
public class ImprovedFileMapBuildingStrategy extends MapBuildingStrategy {

    private final String pathFile;

    public ImprovedFileMapBuildingStrategy(String pathFile, float tileWidth, float tileHeight) {
        super(1, tileWidth, tileHeight);
        this.pathFile = pathFile;
    }

    @Override
    public void build(Map map) {
        this.rooms = new ArrayList<>();
        MapReader mapReader = new MapReader();
        MapFile mapFile = mapReader.buildMapFormFile(pathFile);
        //creating the rooms
        int[][] mainMap = mapFile.getMap();
        for (int i = 0; i < mainMap.length; i++) {
            for (int j = 0; j < mainMap[0].length; j++) {
                RoomFile roomFile = mapFile.findRoomFile(mainMap[i][j]);
                if (roomFile != null) {
                    //the ImprovedFileRoomBuildingStrategy already draw the doorTile on the room
                    RoomBuildingStrategy roomBuildingStrategy = new ImprovedFileRoomBuildingStrategy(roomFile, tileWidth, tileHeight);
                    Room newRoom = new Room(roomFile.getId(), roomBuildingStrategy);
                    newRoom.setMap(map);
                    rooms.add(newRoom);
                    //for now, when 1 is the id of room, I say its the first room
                    if (roomFile.getId() == 1) {
                        this.firstRoom = newRoom;
                    }
                }
            }
        }
        if (this.firstRoom == null) {
            this.firstRoom = rooms.get(0);
        }
        //connecting rooms
        //this only works if the rooms are neighboors and each one has only one door on his side
        for (int i = 0; i < mainMap.length; i++) {
            for (int j = 0; j < mainMap[0].length; j++) {
                if (mainMap[i][j] > 0) {
                    Room roomOne = getRoomById(mainMap[i][j]);
                    //right
                    if (j < mainMap[0].length - 1 && mainMap[i][j + 1] > 0) {
                        Room roomTwo = getRoomById(mainMap[i][j + 1]);
                        Tile[] doorTilesRoomOne = roomOne.getTilesOfType(Tile.DOOR_TILE);
                        Tile[] doorTilesRoomTwo = roomTwo.getTilesOfType(Tile.DOOR_TILE);
                        DoorTile doorRoomOne = null, doorRoomTwo = null;
                        for (int k = 0; k < doorTilesRoomOne.length; k++) {
                            if (doorTilesRoomOne[k].getTileX() == roomOne.getRoomWidth() - 1) {
                                doorRoomOne = (DoorTile) doorTilesRoomOne[k];
                                break;
                            }
                        }
                        for (int k = 0; k < doorTilesRoomTwo.length; k++) {
                            if (doorTilesRoomTwo[k].getTileX() == 0) {
                                doorRoomTwo = (DoorTile) doorTilesRoomTwo[k];
                                break;
                            }
                        }
                        if (doorRoomOne != null && doorRoomTwo != null) {
                            connectRooms(roomOne, doorRoomOne.getTileX(), doorRoomOne.getTileY(), roomTwo, doorRoomTwo.getTileX(), doorRoomTwo.getTileY());
                        }
                    }
                    //down
                    if (i < mainMap.length - 1 && mainMap[i + 1][j] > 0) {
                        Room roomTwo = getRoomById(mainMap[i + 1][j]);
                        Tile[] doorTilesRoomOne = roomOne.getTilesOfType(Tile.DOOR_TILE);
                        Tile[] doorTilesRoomTwo = roomTwo.getTilesOfType(Tile.DOOR_TILE);
                        DoorTile doorRoomOne = null, doorRoomTwo = null;
                        for (int k = 0; k < doorTilesRoomOne.length; k++) {
                            if (doorTilesRoomOne[k].getTileY() == roomOne.getRoomHeight() - 1) {
                                doorRoomOne = (DoorTile) doorTilesRoomOne[k];
                                break;
                            }
                        }
                        for (int k = 0; k < doorTilesRoomTwo.length; k++) {
                            if (doorTilesRoomTwo[k].getTileY() == 0) {
                                doorRoomTwo = (DoorTile) doorTilesRoomTwo[k];
                                break;
                            }
                        }
                        if (doorRoomOne != null && doorRoomTwo != null) {
                            connectRooms(roomOne, doorRoomOne.getTileX(), doorRoomOne.getTileY(), roomTwo, doorRoomTwo.getTileX(), doorRoomTwo.getTileY());
                        }
                    }
                }
            }
        }
    }

}

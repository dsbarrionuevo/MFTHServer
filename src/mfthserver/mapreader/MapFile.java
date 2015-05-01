package mfthserver.mapreader;

/**
 *
 * @author Barrionuevo Diego
 */
public class MapFile {

    private int id;
    private int[][] map;
    private TileType[] tileTypes;
    private RoomType[] roomTypes;
    private RoomFile[] rooms;

    public MapFile() {
    }

    public TileType findTileType(int id) {
        for (int i = 0; i < tileTypes.length; i++) {
            if (tileTypes[i].getId() == id) {
                return tileTypes[i];
            }
        }
        return null;
    }

    public RoomType findRoomType(int id) {
        for (int i = 0; i < roomTypes.length; i++) {
            if (roomTypes[i].getId() == id) {
                return roomTypes[i];
            }
        }
        return null;
    }
    
    public RoomFile findRoomFile(int id){
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getId() == id) {
                return rooms[i];
            }
        }
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public void setTileTypes(TileType[] tileTypes) {
        this.tileTypes = tileTypes;
    }

    public void setRoomTypes(RoomType[] roomTypes) {
        this.roomTypes = roomTypes;
    }

    public void setRooms(RoomFile[] rooms) {
        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }

    public int[][] getMap() {
        return map;
    }

    public TileType[] getTileTypes() {
        return tileTypes;
    }

    public RoomType[] getRoomTypes() {
        return roomTypes;
    }

    public RoomFile[] getRooms() {
        return rooms;
    }

}

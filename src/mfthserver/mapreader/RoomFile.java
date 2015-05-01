package mfthserver.mapreader;

/**
 *
 * @author Barrionuevo Diego
 */
public class RoomFile {

    private int id;
    private RoomType roomType;
    private int[][] map;

    public RoomFile(int id, RoomType roomType, int[][] map) {
        this.id = id;
        this.roomType = roomType;
        this.map = map;
    }

    public int getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int[][] getMap() {
        return map;
    }

}

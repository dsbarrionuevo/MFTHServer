package mfthserver.map.tiles;

import mfthserver.map.room.Room;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Barrionuevo Diego
 */
public class DoorTile extends Tile {

    private Room myRoom;
    private DoorTile connectedTo;

    public DoorTile(Room myRoom, DoorTile connectedTo, int tileX, int tileY, float width, float height) {
        super(tileX, tileY, width, height, Color.lightGray, true);
    }

    public DoorTile(int tileX, int tileY, float width, float height) {
        this(null, null, tileX, tileY, width, height);
        this.myRoom = null;
        this.connectedTo = null;
    }

    @Deprecated
    public DoorTile(Room myRoom, DoorTile connectedTo, Vector2f position, float width, float height) {
        super(position, width, height, Color.lightGray, true);
        this.myRoom = myRoom;
        this.connectedTo = connectedTo;
    }

    @Override
    public int getType() {
        return Tile.DOOR_TILE;
    }

    public Room getMyRoom() {
        return myRoom;
    }

    public DoorTile getConnectedTo() {
        return connectedTo;
    }

    public void setMyRoom(Room myRoom) {
        this.myRoom = myRoom;
    }

    public void setConnectedTo(DoorTile connectedTo) {
        this.connectedTo = connectedTo;
    }

}

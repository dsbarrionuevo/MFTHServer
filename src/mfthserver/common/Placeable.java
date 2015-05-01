package mfthserver.common;

import mfthserver.map.room.Room;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Diego
 */
public interface Placeable {

    public void setPosition(Vector2f position);

    public Vector2f getPosition();

    public float getWidth();

    public float getHeight();

    public void setRoom(Room room);

    public Room getRoom();

    //
    public void update(GameContainer container, int delta);

}

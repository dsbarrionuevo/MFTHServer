package mfthserver.common;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Diego
 */
public abstract class Movable extends Drawable {

    protected float speed;

    public Movable(float speed, Vector2f position, Shape body) {
        super(position, body, true);
        this.speed = speed;
    }

    public abstract void update(GameContainer container, int delta);

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}

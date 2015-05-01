package mfthserver.map.tiles;

import mfthserver.common.Drawable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Diego
 */
public abstract class Tile extends Drawable {

    public static final int BLANK_TILE = 0;
    public static final int WALL_TILE = 1;
    public static final int DOOR_TILE = 2;

    protected Color color;
    protected boolean walkable;
    //
    protected int tileX, tileY;
    protected Image image;

    public Tile(int tileX, int tileY, float width, float height, Color color, boolean walkable) {
        super(new Vector2f(tileX * width, tileY * height), new Rectangle(0, 0, width, height));
        this.tileX = tileX;
        this.tileY = tileY;
        this.color = color;
        this.walkable = walkable;
    }

    @Deprecated
    public Tile(Vector2f position, float width, float height, Color color, boolean walkable) {
        super(position, new Rectangle(0, 0, width, height));
        this.tileX = (int) (Math.floor(position.x / width));
        this.tileY = (int) (Math.floor(position.y / height));
        this.color = color;
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public abstract int getType();

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public String getId() {
        return this.getTileX() + "_" + this.getTileY();
    }

    @Override
    public boolean equals(Object obj) {
        return ((Tile) obj).getId().equalsIgnoreCase(this.getId());
    }

    //
    public void setImage(Image image) {
        this.image = image;
    }

}

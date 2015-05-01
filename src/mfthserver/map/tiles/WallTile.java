package mfthserver.map.tiles;

import org.newdawn.slick.Color;

/**
 *
 * @author Diego
 */
public class WallTile extends Tile {

    public WallTile(int tileX, int tileY, float width, float height) {
        super(tileX, tileY, width, height, Color.gray, false);
    }

    @Override
    public int getType() {
        return Tile.WALL_TILE;
    }

}

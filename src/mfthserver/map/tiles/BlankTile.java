package mfthserver.map.tiles;

import org.newdawn.slick.Color;

/**
 *
 * @author Diego
 */
public class BlankTile extends Tile {

    public BlankTile(int tileX, int tileY, float width, float height) {
        super(tileX, tileY, width, height, Color.white, true);
    }

    @Override
    public int getType() {
        return Tile.BLANK_TILE;
    }

}

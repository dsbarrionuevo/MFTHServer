package mfthserver.map.room.buildingstrategies;

import mfthserver.map.tiles.Tile;
import mfthserver.map.tiles.WallTile;

/**
 *
 * @author Barrionuevo Diego
 */
public class RandomRoomBuildingStrategy extends RoomBuildingStrategy {

    public RandomRoomBuildingStrategy(int widthLength, int heightLength, float tileWidth, float tileHeight) {
        super(widthLength, heightLength, tileWidth, tileHeight);
    }

    @Override
    public Tile[][] build() {
        Tile[][] map = new Tile[heightLength][widthLength];
        borderMap(map);
        int y = heightLength - 2, x = (int) (Math.random() * (widthLength - 2)) + 1;
        map[y][x] = new WallTile(x, y, tileWidth, tileHeight);
        return map;

    }

}

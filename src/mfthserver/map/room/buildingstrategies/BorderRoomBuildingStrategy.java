package mfthserver.map.room.buildingstrategies;

import mfthserver.map.tiles.Tile;

/**
 *
 * @author Diego
 */
public class BorderRoomBuildingStrategy extends RoomBuildingStrategy {

    public BorderRoomBuildingStrategy(int widthLength, int heightLength, float tileWidth, float tileHeight) {
        super(widthLength, heightLength, tileWidth, tileHeight);
    }

    @Override
    public Tile[][] build() {
        Tile[][] map = new Tile[heightLength][widthLength];
        borderMap(map);
        return map;
    }

}

package mfthserver.map.room.buildingstrategies;

import java.util.logging.Level;
import java.util.logging.Logger;
import mfthserver.map.tiles.BlankTile;
import mfthserver.map.tiles.DoorTile;
import mfthserver.map.tiles.Tile;
import mfthserver.map.tiles.WallTile;
import mfthserver.mapreader.RoomFile;
import mfthserver.mapreader.RoomType;
import mfthserver.mapreader.TileFile;
import mfthserver.mapreader.TileType;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Barrionuevo Diego
 */
public class ImprovedFileRoomBuildingStrategy extends RoomBuildingStrategy {

    private RoomFile roomFile;

    public ImprovedFileRoomBuildingStrategy(RoomFile roomFile, float tileWidth, float tileHeight) {
        super(roomFile.getMap()[0].length, roomFile.getMap().length, tileWidth, tileHeight);
        this.roomFile = roomFile;
    }

    @Override
    public Tile[][] build() {
        Tile[][] map = new Tile[heightLength][widthLength];
        RoomType roomType = roomFile.getRoomType();
        int[][] roomMap = roomFile.getMap();
        for (int i = 0; i < roomMap.length; i++) {
            for (int j = 0; j < roomMap[0].length; j++) {
                TileFile tileFile = roomType.findTileFile(roomMap[i][j]);
                if (tileFile != null) {
                    TileType tileType = tileFile.getTileType();
                    if ("empty".equalsIgnoreCase(tileType.getName())) {
                        map[i][j] = new BlankTile(j, i, tileWidth, tileHeight);
                    } else if ("wall".equalsIgnoreCase(tileType.getName())) {
                        map[i][j] = new WallTile(j, i, tileWidth, tileHeight);
                    } else if ("door".equalsIgnoreCase(tileType.getName())) {
                        map[i][j] = new DoorTile(j, i, tileWidth, tileHeight);
                    }
                    /*
                    try {
                        Image image = new Image("res/images/" + tileFile.getResourcePath());
                        map[i][j].setImage(image);
                    } catch (SlickException ex) {
                        Logger.getLogger(ImprovedFileRoomBuildingStrategy.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
                } else {
                    //shouldnt go this line...
                    map[i][j] = new BlankTile(j, i, tileWidth, tileHeight);
                }
            }
        }
        return map;
    }

}

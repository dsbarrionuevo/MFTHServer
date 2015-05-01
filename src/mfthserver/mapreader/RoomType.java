package mfthserver.mapreader;

/**
 *
 * @author Barrionuevo Diego
 */
public class RoomType {

    private int id;
    private String name;
    private TileFile[] tiles;

    public RoomType(int id, String name, TileFile[] tiles) {
        this.id = id;
        this.name = name;
        this.tiles = tiles;
    }

    public TileFile findTileFile(int id) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getId() == id) {
                return tiles[i];
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TileFile[] getTiles() {
        return tiles;
    }

}

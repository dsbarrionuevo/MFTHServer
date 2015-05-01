package mfthserver.mapreader;

/**
 *
 * @author Barrionuevo Diego
 */
public class TileType {

    private int id;
    private String name;
    private boolean walkeable;
    private boolean door;

    public TileType(int id, String name, boolean walkeable, boolean door) {
        this.id = id;
        this.name = name;
        this.walkeable = walkeable;
        this.door = door;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isWalkeable() {
        return walkeable;
    }

    public boolean isDoor() {
        return door;
    }

}

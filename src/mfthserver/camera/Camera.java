package mfthserver.camera;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Barrionuevo Diego
 */
public class Camera {

    private Rectangle visor;
    //
    private static Camera me;
    private int padding;//number of tiles to allow see

    public static Camera getInstance() {
        if (me == null) {
            createCamera();
        }
        return me;
    }

    public static void createCamera(Vector2f initPosition, float width, float height) {
        me = new Camera(initPosition, width, height);
    }

    //Default camera
    public static void createCamera() {
        me = new Camera(new Vector2f(0f, 0f), 800, 600);
    }

    private Camera(Vector2f initPosition, float width, float height) {
        this.visor = new Rectangle(initPosition.x, initPosition.y, width, height);
    }

    public void setPositionX(float x) {
        this.visor.setX(x);
    }

    public Vector2f getPosition() {
        return new Vector2f(visor.getX(), visor.getY());
    }

    public float getPositionX() {
        return visor.getX();
    }

    public float getPositionY() {
        return visor.getY();
    }

    public float getWidth() {
        return visor.getWidth();
    }

    public float getHeight() {
        return visor.getHeight();
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

}

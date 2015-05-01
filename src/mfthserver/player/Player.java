package mfthserver.player;

import java.util.logging.Level;
import java.util.logging.Logger;
import mfthserver.common.Movable;
import mfthserver.common.Placeable;
import mfthserver.map.room.Room;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Diego
 */
public class Player extends Movable implements Placeable {

    private Room room;
    //
    private long timerHitTheDoor;
    private long timerToHitTheDoor;
    //
    private Animation walkingFront, walkingBack, walkingLeft, walkingRight;

    public Player() {
        super(10f, new Vector2f(), new Rectangle(0, 0, 32, 32));
        this.timerHitTheDoor = 0;
        this.timerToHitTheDoor = 1 * 1000;
        //
        setupAnimations();
    }

    @Override
    public void update(GameContainer container, int delta) {
        move(container, delta);
        //check if stand on door
        checkHitDoor(delta);
    }

    private void checkHitDoor(int delta) {
        if (this.timerHitTheDoor > this.timerToHitTheDoor) {
            if (room.hitTheDoor(this)) {
                this.timerHitTheDoor = 0;
            }
        }
        this.timerHitTheDoor += delta;
    }

    private void move(GameContainer container, int delta) {
        Input input = container.getInput();
        float moveFactor = speed * (delta / 100f);
        if (input.isKeyDown(Input.KEY_LEFT) && room.canMoveTo(this, Room.DIRECTION_WEST) && room.movingInsideCamera(this, moveFactor, Room.DIRECTION_WEST)) {
            position.x -= moveFactor;
            updateAnimation(delta);
        }
        if (input.isKeyDown(Input.KEY_RIGHT) && room.canMoveTo(this, Room.DIRECTION_EAST) && room.movingInsideCamera(this, moveFactor, Room.DIRECTION_EAST)) {
            position.x += moveFactor;
            updateAnimation(delta);
        }
        if (input.isKeyDown(Input.KEY_UP) && room.canMoveTo(this, Room.DIRECTION_NORTH) && room.movingInsideCamera(this, moveFactor, Room.DIRECTION_NORTH)) {
            position.y -= moveFactor;
            updateAnimation(delta);
        }
        if (input.isKeyDown(Input.KEY_DOWN) && room.canMoveTo(this, Room.DIRECTION_SOUTH) && room.movingInsideCamera(this, moveFactor, Room.DIRECTION_SOUTH)) {
            position.y += moveFactor;
            updateAnimation(delta);
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            graphic = walkingLeft;
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            graphic = walkingRight;
        } else if (input.isKeyDown(Input.KEY_UP)) {
            graphic = walkingBack;
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            graphic = walkingFront;
        }
        if (!input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN)) {
            if (graphic != null) {
                ((Animation) graphic).stop();
                ((Animation) graphic).setCurrentFrame(0);
            }
        }
    }

    private void updateAnimation(int delta) {
        if (graphic != null) {
            if (((Animation) graphic).isStopped()) {
                ((Animation) graphic).start();
            }
            ((Animation) graphic).update(delta);
        }
    }

    private void setupAnimations() {
        try {
            //speed: 10px ==> duration: 340 milis
            int duration = 340;
            //animations
            String model = "model1";
            this.walkingFront = new Animation(new Image[]{
                new Image("res/images/players/" + model + "/front0.png"),
                new Image("res/images/players/" + model + "/front1.png"),
                new Image("res/images/players/" + model + "/front2.png")
            }, duration, true);
            this.walkingBack = new Animation(new Image[]{
                new Image("res/images/players/" + model + "/back0.png"),
                new Image("res/images/players/" + model + "/back1.png"),
                new Image("res/images/players/" + model + "/back2.png")
            }, duration, true);
            this.walkingLeft = new Animation(new Image[]{
                new Image("res/images/players/" + model + "/left0.png"),
                new Image("res/images/players/" + model + "/left1.png"),
                new Image("res/images/players/" + model + "/left2.png")
            }, duration, true);
            this.walkingRight = new Animation(new Image[]{
                new Image("res/images/players/" + model + "/right0.png"),
                new Image("res/images/players/" + model + "/right1.png"),
                new Image("res/images/players/" + model + "/right2.png")
            }, duration, true);
            setGraphic(walkingFront);
            ((Animation) graphic).stop();
        } catch (SlickException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setPosition(Vector2f position) {
        this.position = position;
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public Room getRoom() {
        return this.room;
    }

}

package mfthserver.server.commands;

import org.json.JSONObject;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommandMoveResponse extends Command {

    private int clientId;
    private boolean canMove;
    private Vector2f position;

    public CommandMoveResponse() {
        super(CommandFactory.COMMAND_MOVE_REPSONSE);
    }

    public CommandMoveResponse(int clientId, boolean canMove, Vector2f position) {
        this();
        this.clientId = clientId;
        this.canMove = canMove;
        this.position = position;
    }

    @Override
    public String getRepresentation() {
        return "{command:"+CommandFactory.COMMAND_MOVE_REPSONSE+", client_id: " + clientId + ", can_move:" + canMove + ", position: {x:" + position.x + ", y:" + position.y + "}}";
    }

    @Override
    public Command fromRepresentation(String representation) {
        CommandMoveResponse result;
        JSONObject jsonCommand = new JSONObject(representation);
        JSONObject jsonPosition = jsonCommand.getJSONObject("position");
        Vector2f position = new Vector2f((float) jsonPosition.getDouble("x"), (float) jsonPosition.getDouble("y"));
        result = new CommandMoveResponse(jsonCommand.getInt("client_id"), jsonCommand.getBoolean("can_move"), position);
        return result;
    }

    public int getClientId() {
        return clientId;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public Vector2f getPosition() {
        return position;
    }

}

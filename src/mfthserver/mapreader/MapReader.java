package mfthserver.mapreader;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import system.SystemIO;

/**
 *
 * @author Barrionuevo Diego
 */
public class MapReader {

    private MapFile map;

    public MapReader() {

    }

    public MapFile buildMapFormFile(String mapFileName) {
        try {
            String mapFile = SystemIO.readFile(mapFileName);
            map = new MapFile();
            JSONObject root = new JSONObject(mapFile.trim());
            map.setId(root.getInt("id_map"));
            map.setMap(getMap(root, "map"));
            JSONArray tileTypesJson = root.getJSONArray("tile_types");
            TileType[] tileTypes = new TileType[tileTypesJson.length()];
            for (int i = 0; i < tileTypesJson.length(); i++) {
                JSONObject currentTileTypeJson = tileTypesJson.getJSONObject(i);
                tileTypes[i] = new TileType(
                        currentTileTypeJson.getInt("id_tile_type"),
                        currentTileTypeJson.getString("name"),
                        currentTileTypeJson.getBoolean("walkable"),
                        currentTileTypeJson.getBoolean("door")
                );
            }
            map.setTileTypes(tileTypes);
            JSONArray roomTypesJson = root.getJSONArray("room_types");
            RoomType[] roomTypes = new RoomType[roomTypesJson.length()];
            for (int i = 0; i < roomTypesJson.length(); i++) {
                JSONObject currentRoomTypeJson = roomTypesJson.getJSONObject(i);
                JSONArray tilesFileJson = currentRoomTypeJson.getJSONArray("tiles");
                TileFile[] tilesFile = new TileFile[tilesFileJson.length()];
                for (int j = 0; j < tilesFileJson.length(); j++) {
                    JSONObject currentTileFileJson = tilesFileJson.getJSONObject(j);
                    tilesFile[j] = new TileFile(
                            currentTileFileJson.getInt("id_tile"),
                            map.findTileType(currentTileFileJson.getInt("tile_type")),
                            currentTileFileJson.getString("image")
                    );
                }
                roomTypes[i] = new RoomType(
                        currentRoomTypeJson.getInt("id_room_type"),
                        currentRoomTypeJson.getString("name"),
                        tilesFile
                );
            }
            map.setRoomTypes(roomTypes);

            JSONArray roomsJson = root.getJSONArray("rooms");
            RoomFile[] rooms = new RoomFile[roomsJson.length()];
            for (int i = 0; i < roomsJson.length(); i++) {
                JSONObject currentRoomFileJson = roomsJson.getJSONObject(i);
                rooms[i] = new RoomFile(
                        currentRoomFileJson.getInt("id_room"),
                        map.findRoomType(currentRoomFileJson.getInt("room_type")),
                        getMap(currentRoomFileJson, "map")
                );
            }
            map.setRooms(rooms);
        } catch (Exception ex) {
            Logger.getLogger(MapReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.map;
    }

    public static int[][] getMap(JSONObject obj, String mapName) {
        int[][] mapCoors;
        JSONArray mapRows = obj.getJSONArray(mapName);
        mapCoors = new int[mapRows.length()][mapRows.getJSONArray(0).length()];
        for (int i = 0; i < mapRows.length(); i++) {
            JSONArray currentRow = mapRows.getJSONArray(i);
            for (int j = 0; j < currentRow.length(); j++) {
                mapCoors[i][j] = Integer.parseInt(currentRow.get(j).toString());
            }
        }
        return mapCoors;
    }

    public static void main(String[] args) {
        new MapReader().buildMapFormFile("res/map2.txt");
    }
}

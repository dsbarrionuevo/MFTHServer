package mfthserver.map.buildingstrategies;

import java.util.ArrayList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.*;
import java.util.logging.Logger;
import mfthserver.map.Map;
import mfthserver.map.room.Room;
import mfthserver.map.room.buildingstrategies.BorderRoomBuildingStrategy;
import mfthserver.map.room.buildingstrategies.RoomBuildingStrategy;
import system.SystemIO;

/**
 *
 * @author Barrionuevo Diego
 */
public class FileMapBuildingStrategy extends MapBuildingStrategy {

    private String pathFile;

    public FileMapBuildingStrategy(String pathFile, float tileWidth, float tileHeight) {
        super(0, tileWidth, tileHeight);
        this.pathFile = pathFile;
    }

    @Override
    public void build(Map map) {
        try {
            //
            this.rooms = new ArrayList<>();
            int widthRoom = 16;
            int heightRoom = 12;
            //
            String mapString = SystemIO.readFile(this.pathFile);
            System.out.println(mapString);
            String[] rows = mapString.split(",");
            //
            Pattern pattern = Pattern.compile("(\\{.+?\\})");
            Matcher matcher = pattern.matcher(mapString);
            while (matcher.find()) {
                String foundSection = matcher.group(1);
                String titleSection = getTitleSection(foundSection);
                String bodySection = getBodySection(foundSection);
                String titleName;
                int titleType;
                titleSection = titleSection.substring(1, titleSection.length() - 1);
                System.out.println(titleSection);
                String[] titleParts = titleSection.split(":");
                titleName = titleParts[0];
                titleType = Integer.parseInt(titleParts[1]);
                //
                if (titleType == 0)//Main map
                {
                    int[][] mapArray = getMapRows(bodySection);
                    //find the bigger id room so later I know when stop looping
                    int biggerRoomId = 0;
                    for (int i = 0; i < mapArray.length; i++) {
                        for (int j = 0; j < mapArray[0].length; j++) {
                            if (mapArray[i][j] > biggerRoomId) {
                                biggerRoomId = mapArray[i][j];
                            }
                        }
                    }
                    //add all the rooms
                    int roomId = 1;
                    while (roomId <= biggerRoomId) {
                        for (int i = 0; i < mapArray.length; i++) {
                            for (int j = 0; j < mapArray[0].length; j++) {
                                if (mapArray[i][j] == roomId) {
                                    widthRoom = (int) (Math.random() * 10) + 6;
                                    heightRoom = (int) (Math.random() * 10) + 6;
                                    RoomBuildingStrategy roomBuildingStrategy = new BorderRoomBuildingStrategy(widthRoom, heightRoom, tileWidth, tileHeight);
                                    Room newRoom = new Room(roomId, roomBuildingStrategy);
                                    newRoom.setMap(map);
                                    this.rooms.add(newRoom);
                                    roomId++;
                                }
                            }
                        }
                    }
                    //add connections between rooms
                    for (Room room : rooms) {
                        for (int i = 0; i < mapArray.length; i++) {
                            for (int j = 0; j < mapArray[0].length; j++) {
                                if (mapArray[i][j] == room.getRoomId()) {
                                    //up
                                    if (i > 0 && mapArray[i - 1][j] != 0) {
                                        Room otherRoom = getRoomById(mapArray[i - 1][j]);
                                        int randomPositionCurrentRoom = ((int) (Math.random() * (room.getRoomWidth() - 2)) + 1);
                                        int randomPositionNextRoom = ((int) (Math.random() * (otherRoom.getRoomWidth() - 2)) + 1);
                                        connectRooms(room, randomPositionCurrentRoom, 0, otherRoom, randomPositionNextRoom, otherRoom.getRoomHeight() - 1);
                                    }
                                    //right
                                    if (j < mapArray[0].length - 1 && mapArray[i][j + 1] != 0) {
                                        Room otherRoom = getRoomById(mapArray[i][j + 1]);
                                        int randomPositionCurrentRoom = ((int) (Math.random() * (room.getRoomHeight() - 2)) + 1);
                                        int randomPositionNextRoom = ((int) (Math.random() * (otherRoom.getRoomHeight() - 2)) + 1);
                                        connectRooms(room, room.getRoomWidth() - 1, randomPositionCurrentRoom, otherRoom, 0, randomPositionNextRoom);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.firstRoom = getRoomById(1);
        } catch (IOException ex) {
            Logger.getLogger(FileMapBuildingStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int[][] getMapRows(String source) {
        ArrayList<String[]> mapRows = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\[.*?\\])");
        Matcher matcher = pattern.matcher(source);
        int cellsLength = -1;
        while (matcher.find()) {
            String foundMapRow = matcher.group(1);
            //remove the []
            String mapRow = foundMapRow.substring(1, foundMapRow.length() - 1);
            String[] cells = mapRow.split(",");
            cellsLength = cells.length;
            mapRows.add(cells);
        }
        int[][] result = new int[mapRows.size()][cellsLength];
        for (int i = 0; i < mapRows.size(); i++) {
            String[] cells = mapRows.get(i);
            for (int j = 0; j < cells.length; j++) {
                result[i][j] = Integer.parseInt(cells[j]);
            }
        }
        return result;
    }

    private String getTitleSection(String section) {
        Pattern pattern = Pattern.compile("(\\[\\w+:\\d+\\])");
        Matcher matcher = pattern.matcher(section);
        if (matcher.find()) {
            String foundTitleSection = matcher.group(1);
            return foundTitleSection;
        }
        return null;
    }

    private String getBodySection(String section) {
        Pattern pattern = Pattern.compile("(\\[.*?\\])");
        Matcher matcher = pattern.matcher(section);
        String result = "";
        boolean skippedTitle = false;
        while (matcher.find()) {
            if (!skippedTitle) {
                skippedTitle = true;
                continue;
            }
            result += matcher.group(1);
        }
        return result;
    }

}

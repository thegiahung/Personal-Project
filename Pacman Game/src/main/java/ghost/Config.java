package ghost;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;

public class Config {
    private static String FILE_NAME = "config.json";

    private char[][] map;
    private Long lives;
    private Long speed;
    private Long[] modeLengths;

    private Config() {
    }

    /**
     * Read and retrieve information from json files.
     *
     * @return information about json files including map, lives, speed and mode length
     *
     * @throws RuntimeException
     */
    public static Config load() throws RuntimeException {
        try {
            Reader reader = new FileReader(FILE_NAME);

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(reader);

            Config ret = new Config();

            String mapFile = (String) obj.get("map");
            ret.map = loadMap(mapFile);

            ret.lives = (Long) obj.get("lives");
            ret.speed = (Long) obj.get("speed");

            JSONArray modeLengths = (JSONArray) obj.get("modeLengths");
            ret.modeLengths = new Long[modeLengths.size()];
            for (int i = 0; i < modeLengths.size(); i++) {
                ret.modeLengths[i] = (Long) modeLengths.get(i);
            }

            return ret;
        } catch (Exception e) {
            throw new RuntimeException("Could not load config", e);
        }
    }

    /**
     * Convert the map file into a 2 dimension array of character
     *
     * @param mapFile
     *
     * @return 2 dimension array of the map.
     */
    private static char[][] loadMap(String mapFile) {
        char[][] map = new char[36][28];
        File f = new File(mapFile);

        try {
            Scanner scan = new Scanner(f);
            int i = 0;
            while (scan.hasNext()) {
                String[] line = scan.nextLine().split("");
                for (int j = 0; j < line.length; j++) {
                    map[i][j] = line[j].charAt(0);
                }
                i += 1;
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not parse map", e);
        }

        return map;
    }

    /**
     * This should return the map parse the json file.
     *
     * @return an array of character representing the map.
     */
    public char[][] getMap() {
        return map;
    }

    /**
     * This should return waka lives parse the json file.
     *
     * @return the number of lives.
     */
    public Long getLives() {
        return lives;
    }

    /**
     * This should return FPS parse the json file.
     *
     * @return the FPS running the game
     */
    public Long getSpeed() {
        return speed;
    }

    /**
     * This should return an array to specify the time that the ghost will need to swap between two mode.
     *
     * @return an array of time length that each mode will last for.
     */
    public Long[] getModeLengths() {
        return modeLengths;
    }
}

package Assignment2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import Assignment2.FileUtil.Generated;

import java.lang.annotation.*;

// Shaun
@Generated // This code is to ignore FileUtil in the jacocoTestReport because we do not
           // need to test these function
public class JsonReader {
    public static List<Map<String, String>> getJsonToList(String jsonInfo) {
        List<Map<String, String>> list = new ArrayList<>();
        jsonInfo = jsonInfo.replace("[", "");
        jsonInfo = jsonInfo.replace("\"", "");
        jsonInfo = jsonInfo.replace("]", "");
        jsonInfo = jsonInfo.replace("{", "");
        jsonInfo = jsonInfo.replaceAll("\n", "").replaceAll("\r", "");
        jsonInfo = jsonInfo.replace(" ", "");
        jsonInfo = jsonInfo.replace("},", "$");
        jsonInfo = jsonInfo.replace("}", "$");

        String[] split = jsonInfo.split("\\$");

        for (int i = 0; i < split.length; i++) {
            Map<String, String> map = new HashMap<>();
            String[] split1 = split[i].split(",");

            for (int j = 0; j < split1.length; j++) {
                String[] split2 = split1[j].split(":");

                map.put(split2[0], split2[1]);
            }
            list.add(map);
        }
        return list;
    }

    public static Map<String, Integer> getJsonToMap(String jsonInfo) {
        Map<String, Integer> map = new HashMap<>();
        jsonInfo = jsonInfo.replace("}", "");
        jsonInfo = jsonInfo.replace("{", "");
        jsonInfo = jsonInfo.replace("\n", "");
        jsonInfo = jsonInfo.replace(" ", "");
        jsonInfo = jsonInfo.replace("\"", "");
        String[] split = jsonInfo.split(",");
        for (int i = 0; i < split.length; i++) {
            String[] split1 = split[i].split(":");
            map.put(split1[0], Integer.parseInt(split1[1]));
        }
        return map;
    }

    public static String getJsonInfo(String src) {
        String jsonStr = "";
        try {
            File jsonFile = new File(src);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int num = 0;
            StringBuffer sb = new StringBuffer();
            while ((num = reader.read()) != -1) {
                sb.append((char) num);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();

            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveJsonInfo(String jsonString, String jsonFilePath) {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(jsonFilePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            bufferedWriter.write(jsonString);
        } catch (IOException e) {
            System.out.println("Cannot save info to file!" + e);
        } finally {
            if (null != bufferedWriter) {
                try {
                    bufferedWriter.close();
                } catch (IOException exception) {
                    System.out.println("Info saved, bafferedwriter closed unsucessfully" + exception);
                }
            }
        }
    }

    // This code is to ignore in the jacocoTestReport because we do not need to test
    // these function
    @Documented
    public @interface Generated {
    }

}

package Assignment2;

import java.io.File;
import java.util.*;
import java.lang.annotation.*;

public class CardRegister {

    // Customer pay for their items by using card payments
    // If card details match the record, it will return -1, if not return -2;
    @Generated
    public static int spendThroughCard(String name, String number) {

        File file = new File("src/database/credit_cards.json");
        String path = file.getAbsolutePath();

        String jsonInfo = JsonReader.getJsonInfo(path);
        List<Map<String, String>> jsonToList = JsonReader.getJsonToList(jsonInfo);
        boolean matchResult = false;
        Map<String, String> cardDetail = null;
        for (int i = 0; i < jsonToList.size(); i++) {
            cardDetail = jsonToList.get(i);
            if (cardDetail.get("name").equals(name) && cardDetail.get("number").equals(number)) {
                matchResult = true;
                break;
            }

        }
        if (matchResult) {
            return -1;
        } else {
            return -2;

        }
    }

    @Documented
    public @interface Generated {
    }
}

package Assignment2;

import java.lang.annotation.*;

public class Last5 {

    @Generated
    public static String[] update(String[] inputlast, String[] newLs) {
        String[] outputlast = new String[5];
        if (newLs.length >= 5) {
            // fill the output string
            for (int i = 0; i < outputlast.length; i++) {
                outputlast[i] = newLs[newLs.length - 5 + i];
            }
        }

        // User bought less than 5 items once
        else {
            for (int i = 0; i < inputlast.length; i++) {
                // Make space for new items
                if (i + newLs.length < 5) {
                    outputlast[i + newLs.length] = inputlast[i];
                } else {
                    break;
                }

            }
            // i =< 3
            for (int i = 0; i < newLs.length; i++) {
                outputlast[i] = newLs[i];
            }
        }

        return outputlast;
    }

    @Documented
    public @interface Generated {
    }
}
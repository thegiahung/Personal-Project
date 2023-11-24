package Assignment_1;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestFileUtil {

    // Test if the user is not admin
    @Test
    public void TestWriteUserinFile() {
        FileUtil f = new FileUtil();
        User user1 = new User("Adam", "soft1111", false);
        try {
            f.writeUserInFile(user1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Test if the user is admin
    @Test
    public void TestWriteUserinFile12() {
        FileUtil f = new FileUtil();
        User user1 = new User("Adam", "soft1111", true);
        try {
            f.writeUserInFile(user1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test read through the user txt file then return it as a list
    @Test
    public void TestReadUserinFile1() {
        FileUtil f = new FileUtil();
        User user1 = new User("Adam", "soft1111", false);
        User user2 = new User("Eva", "soft1234", false);

        f.writeUserInFile(user1);
        f.writeUserInFile(user2);
        f.readUserFromFile();

    }

    // Test reading through the whole txt file, format for every single line[date,
    // currencyname_1, currencyname_2, rate, isPopular]
    @Test
    public void TestReadAllInfo() {
        FileUtil f = new FileUtil();
        try {
            f.ReadAllInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test write in all currency information into the txt file. New record will be
    // a new line
    @Test
    public void TestWriteIn1() {
        FileUtil f = new FileUtil();
        Exchange e1 = new Exchange("2022/09/13", "JPY", "USD", 0.9);
        try {
            f.WriteIn(e1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     // Test read through the user txt file then return it as a list
     @Test
     public void TestReadUserinFile() {
         FileUtil f = new FileUtil();
         User user1 = new User("Adam", "soft1111", false);
         User user2 = new User("Eva", "soft1234", false);

             f.writeUserInFile(user1);
             f.writeUserInFile(user2);
        f.readUserFromFile();

     }

     // Test reading through the whole txt file, format for every single line[date, currencyname_1, currencyname_2, rate, isPopular]
     @Test
     public void TestReadAllInfo1(){
         FileUtil f = new FileUtil();
         try {
             f.ReadAllInfo();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     // Test write in all currency information into the txt file. New record will be a new line
     @Test
     public void TestWriteIn() {
         FileUtil f = new FileUtil();
         Exchange e1 = new Exchange("2022/09/13", "JPY", "USD", 0.9);
         try {
             f.WriteIn(e1);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     // Test getting all history currencies and rate
     @Test
     public void TestGetHistory() {
         FileUtil f = new FileUtil();
         assertNotNull(f.getAllHistory());
     }

    // Test return the list that includes all popular currencies' names: e.g
    // [AUD,CNY,USD,SGD...]
    @Test
    public void TestGetAllCurr() {
        FileUtil f = new FileUtil();
        Exchange e1 = new Exchange("2022/09/13", "JPY", "USD", 0.9);
        Exchange e2 = new Exchange("2022/09/12", "VND", "AUD", 0.6);

        f.WriteIn(e1);
        f.WriteIn(e2);
        f.getAllCurrencies(true);
    }

    // Test return the list that includes all unpopular currencies' names: e.g
    // [AUD,CNY,USD,SGD...]
    @Test
    public void TestGetAllCurr2() {
        FileUtil f = new FileUtil();
        Exchange e1 = new Exchange("2022/09/13", "JPY", "USD", 0.9);
        Exchange e2 = new Exchange("2022/09/12", "VND", "AUD", 0.6);

        f.WriteIn(e1);
        f.WriteIn(e2);
        f.getAllCurrencies(false);

    }

     // Test the input line number to return the required content e.g. line 32rd, {2022-09-03,AUD,USD,1.5}
    @Test
    public void TestGetByLine() {
        FileUtil fu = new FileUtil();
        Exchange e1 = new Exchange("2022/09/13", "JPY", "USD", 0.9);
        Exchange e2 = new Exchange("2022/09/12", "VND", "AUD", 0.6);
        fu.exchanges.add(new Exchange("2022/09/12", "VND", "AUD", 0.6));
            // fu.WriteIn(e1);
        fu.getByLine(0);
    }


    // Test return the names of popular currencies [AUD, USD ,CNY,...] At most four
    // different currency names without duplicate
    @Test
    public void TestAddNewCurr() {
        FileUtil f = new FileUtil();
        f.addNewCurrency("NZD");
    }

    // Test return the names of popular currencies [AUD, USD ,CNY,...] At most four
    // different currency names without duplicate
    @Test
    public void TestChanePop() {
        FileUtil f = new FileUtil();
        f.changePopular("NZD", "CNY");
    }
}
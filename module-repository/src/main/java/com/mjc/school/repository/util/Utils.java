package com.mjc.school.repository.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Random;

public class Utils {

    private static Random rand = new Random();
    private Utils() {
    }

    /**
     * This is reshaped version of previous 'readFromDataSource'.
     * The method takes a file path as a parameter, reads through the
     * data source, writes all the data from data source into the
     * list of Strings and finally returns this list
     *
     * @param filepath
     * @return List
     */
    public static List<String> getTextListFromFile(String filepath) {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return bufferedReader.lines().toList();
        } catch (Exception e) {
            System.out.println("Exception: getTextListFromFile");
        }
        return null;
    }

    /**
     * This method takes List of Strings as a parameter
     * and returns random text/line out of this List
     *
     * @param textList
     * @return String
     */
    public static String getRandomText(List<String> textList) {
        int randomIndex = rand.nextInt(textList.size());
        return textList.get(randomIndex);
    }

    /**
     * The method randomly generates date and time in
     * LocalDateTime format between 2020 and 2024, considering
     * current year months (only January and February) and
     * returns this Date and Time
     *
     * @return LocalDateTime
     */
    public static LocalDateTime getRandomDateTime() {
        // let's take random year from 2020 to 2024
        int year = LocalDateTime.now().getYear() - rand.nextInt(5);
        int month, maxDay, day, hour, min, sec, millisec;
        // max day
        maxDay = 31;

        if (year==2024) {
            // range between 1 (min) and 2 (max)
            month = rand.nextInt((2-1) + 1) + 1;
            if (month==2) maxDay = Year.isLeap(year) ? 29 : 28;
        } else {
            // range between 1 (min) and 12 (max)
            month = rand.nextInt((12-1) + 1) + 1;

            if (month==2) {
                maxDay = Year.isLeap(year) ? 29 : 28;
            }
            else if (month==4 || month==6 || month==9 || month==11) {
                maxDay = 30;
            }
        }

        day = rand.nextInt((maxDay - 1) + 1) + 1;
        hour = rand.nextInt(24);
        min = rand.nextInt(60);
        sec = rand.nextInt(60);
        millisec = rand.nextInt(1000);

        return LocalDateTime.of(year, month, day, hour, min, sec, millisec*1000000);
    }
}
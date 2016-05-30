package com.aconex.binders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
    Created by mukthar.ahmed on 5/29/16.
    GEDCOM record parser util methods
 */
public class RecordProcessor {
    /**
     *  => Extract SirName string from input
     * @param text
     * @return
     */
    public static String extractSirName(String text) {
        String regEx = "/(.*?)/";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String textInBetween = matcher.group(1); // Since (.*?) is capturing group 1
            return textInBetween;
        }

        return null;
    }


    /**
     *  => Extract Name string from input
     * @param text
     * @return
     */
    public static String extractName(String text) {
        String regEx = "(.*?)/(.*?)/$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String textInBetween = matcher.group(1); // Since (.*?) is capturing group 1
            return textInBetween;
        }

        return null;
    }

    public static String extractId(String text) {
        String regEx = "@I(.*?)@";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String textInBetween = matcher.group(1); // Since (.*?) is capturing group 1
            String strConverted = String.format("%5d", Integer.parseInt(textInBetween));
            return strConverted.replaceAll("\\s", "");
        }

        return null;
    }


    public static void main(String[] args) {
        String input = "@I0110@";
        System.out.println(extractId(input));
    }
}

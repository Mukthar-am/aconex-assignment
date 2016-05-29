package com.aconex.binders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mukthar.ahmed on 5/29/16.
 */
public class RecordProcessor {

    public static String process(String input) {

        System.out.println("\nInput String = " + input + "\n");
        System.out.println("Total Lines = " + countLines(input) );

        StringBuilder xmlTagLine = new StringBuilder();

        String[] lineItems = input.split("\\s");
        Integer tabSpaces = Integer.parseInt(lineItems[0]);
        String recordKey = lineItems[1];

        StringBuffer sb = new StringBuffer();
        for (int i = 2; i < lineItems.length; i++) {
            sb.append(lineItems[i]);
        }

        String recordValue = sb.toString();
//        System.out.println("TabSpaces = " + tabSpaces
//            + ", Key = " + recordKey
//            + ", Value = " + recordValue);


        String totalTabs = "";
        for (int space = 0; space <= tabSpaces; space++) {
            totalTabs += "\t";
        }

        if (recordKey.startsWith("@I")) {
            xmlTagLine.append("<" + recordValue.toLowerCase() + " id=\"@I" + extractId(recordKey) + "@\">\n");

        } else {
            /** Using switch to make the code look clean */

            switch (recordKey.toLowerCase()) {
                case "name":
                    xmlTagLine.append(totalTabs + "<name value=\"" + recordValue + ">\n");
                    xmlTagLine.append(totalTabs + "\t<surn>\"" + extractSirName(recordValue) + "</surn>\n");
                    xmlTagLine.append(totalTabs + "\t<givn>\"" + extractName(recordValue) + "</givn>\n");
                    xmlTagLine.append(totalTabs + "</name" + ">\n");

                    break;

                case "sex":
                    xmlTagLine.append(totalTabs + "<sex>" + recordValue + "<sex>\n");
                    break;

                case "birt":
                    xmlTagLine.append(totalTabs + "<birth>" + recordValue + "</birth>\n");
                    break;

                case "occu":
                    xmlTagLine.append(totalTabs + "<occu>" + recordValue + "</occu>\n");
                    break;

                case "title":
                    xmlTagLine.append(totalTabs + "<title>" + recordValue + "</title>\n");
                    break;

                case "fams":
                    xmlTagLine.append(totalTabs + "<fams>" + recordValue + "</fams>\n");
                    break;

                case "famc":
                    xmlTagLine.append(totalTabs + "<famc>" + recordValue + "</famc>\n");
                    break;

                case "note":
                    xmlTagLine.append(totalTabs + "<note>" + recordValue + "</note>\n");
                    break;

                case "chan":
                    xmlTagLine.append(totalTabs + "<chan>" + recordValue + "</chan>\n");
                    break;

                default:
                    xmlTagLine.append("");
                    break;
            }
        }

        System.out.print(xmlTagLine);

        return xmlTagLine.toString();
    }


    private static String extractId(String input) {
        Integer ID = Integer.parseInt(input.substring(2, input.length() - 1));
        return String.format("%01d", ID);
    }

    private static String extractSirName(String text) {
        String regEx = "/(.*?)/";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String textInBetween = matcher.group(1); // Since (.*?) is capturing group 1
            return textInBetween;
        }

        return null;
    }

    private static String extractName(String text) {
        String regEx = "(.*?)/(.*?)/$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String textInBetween = matcher.group(1); // Since (.*?) is capturing group 1
            return textInBetween;
        }

        return null;
    }


    private static int countLines(String input) {
        String[] lines = input.split("\r\n|\n|\r");
        return lines.length;
    }


    public static void main(String[] args) {
        String input = "Elizabeth Alexandra Mary /Windsor/";

        System.out.println("Extracted = " + extractName(input));
    }
}

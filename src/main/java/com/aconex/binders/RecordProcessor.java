package com.aconex.binders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mukthar.ahmed on 5/29/16.
 * <p>
 * Record parser customization.
 */
public class RecordProcessor {

    public static String endRecordTag() {
        return ("</indi>\n");
    }

    public static String process(String input) {
        if (getSubRecordDetails(input).length == 1) {
            return singleRecordParser(input);
        } else {
            return multiRecordParser(input);
        }
    }


    /**
     *  => Single line record input set parser
     * @param input
     * @return
     */
    private static String singleRecordParser(String input) {

        StringBuilder xmlTagLine = new StringBuilder();



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

                default:
                    xmlTagLine.append("");
                    break;
            }
        }

        System.out.print(xmlTagLine.toString());

        return xmlTagLine.toString();

    }


    /**
     *  => Multi line record input set parser
     * @param input
     * @return
     */
    private static String multiRecordParser(String input) {
        StringBuilder xmlTagLine = new StringBuilder();

        String totalTabs = null;
        String[] allSubRecords = getSubRecordDetails(input);
        for (String subRecord : allSubRecords) {

            String[] lineItems = subRecord.split("\\s");
            Integer tabSpaces = Integer.parseInt(lineItems[0]);
            String recordKey = lineItems[1];

            StringBuffer sb = new StringBuffer();
            for (int i = 2; i < lineItems.length; i++) {
                sb.append(lineItems[i]);
            }
            String recordValue = sb.toString();     /** Complete record value string */

            totalTabs = getTabbedString(tabSpaces);  /** Tab spaced string */

            if (recordKey.startsWith("@I")) {
                xmlTagLine.append("<" + recordValue.toLowerCase() + " id=\"@I" + extractId(recordKey) + "@\">\n");

            } else {
                /** Using switch to make the code look clean */
                switch (recordKey.toLowerCase()) {
                    case "birt":
                        xmlTagLine.append(totalTabs + "<birth>\n");
                        break;

                    case "date":
                        xmlTagLine.append(totalTabs + "<date>" + recordValue + "</date>\n");
                        break;

                    case "plac":
                        xmlTagLine.append(totalTabs + "<place>" + recordValue + "</place>\n");
                        break;

                    case "chan":
                        xmlTagLine.append(totalTabs + "<chan>\n");
                        break;

                    default:
                        xmlTagLine.append("");
                        break;
                }
            }

        }

        if (allSubRecords[0].split("\\s")[1].equalsIgnoreCase("birt")) {
            xmlTagLine.append(totalTabs + "</birth>");
        } else {
            xmlTagLine.append(totalTabs + "</chan>");
        }

        System.out.println(xmlTagLine.toString());

        return xmlTagLine.toString();
    }

    /**
     *  => Extract Record ID
     * @param input
     * @return
     */
    public static String extractId(String input) {
        Integer ID = Integer.parseInt(input.substring(2, input.length() - 1));
        return String.format("%01d", ID);
    }


    /**
     *  => Extract SirName string from input
     * @param text
     * @return
     */
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


    /**
     *  => Extract Name string from input
     * @param text
     * @return
     */
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





    /**
     *  => For generating tab spaced string
     * @param tabSpaces
     * @return
     */
    private static String getTabbedString(int tabSpaces) {
        String totalTabs = "";
        for (int space = 0; space < tabSpaces; space++) {
            totalTabs += "\t";
        }

        return totalTabs;
    }


    /**
     * => Testing purpose
     * @param args
     */
    public static void main(String[] args) {
        String input = "Elizabeth Alexandra Mary /Windsor/";

        System.out.println("Extracted = " + extractName(input));
    }
}

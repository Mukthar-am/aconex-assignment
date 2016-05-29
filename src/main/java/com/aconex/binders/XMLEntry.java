package com.aconex.binders;

import java.util.HashMap;

/**
 * Created by mukthar.ahmed on 5/29/16.
 */
public class XMLEntry {

    RawEntryRecord record;

    private String ID;
    private String NAME;
    private String SEX;
    private HashMap<String, String> BIRTH_DETAILS;
    private String TITLE;
    private String FAMC;
    private String FAMS;
    private String CHAN;

    public XMLEntry(String xmlLineSet) {

        String finalLineItem = null;
        String[] tagLines = getSubRecordDetails(xmlLineSet);

        for (String line : tagLines) {
            RawEntryRecord rawEntryRecord = new RawEntryRecord(line);

            if (rawEntryRecord.recordKey.startsWith("@I")) {
                System.out.print(
                    "<" + rawEntryRecord.recordValue.toLowerCase()
                        + " id=\"@I" + extractId(rawEntryRecord.recordKey) + "@\">\n"
                );

            }


            if (rawEntryRecord.recordKey.equalsIgnoreCase("birt")
                || rawEntryRecord.recordKey.equalsIgnoreCase("chan")
                || rawEntryRecord.recordKey.equalsIgnoreCase("date")
                || rawEntryRecord.recordKey.equalsIgnoreCase("plac")) {

                finalLineItem += line + "\n";

            } else {
                if (!finalLineItem.isEmpty()) {
                    OUT_XML.append("\n" + RecordProcessor.process(finalLineItem));
                    finalLineItem = "";
                }
                if (!line.isEmpty()) {
                    OUT_XML.append("\n" + RecordProcessor.process(line));
                }

            }

        }


    public void setId(String inId) {
        this.ID = (
            "<" + record.recordValue.toLowerCase() + " id=\"@I" + extractId(record.recordKey) + "@\">\n");
    }


    public void setName(String inName) {
        this.NAME = inName;


//        xmlTagLine.append(totalTabs + "<name value=\"" + recordValue + ">\n");
//        xmlTagLine.append(totalTabs + "\t<surn>\"" + extractSirName(recordValue) + "</surn>\n");
//        xmlTagLine.append(totalTabs + "\t<givn>\"" + extractName(recordValue) + "</givn>\n");
//        xmlTagLine.append(totalTabs + "</name" + ">\n");
    }

    public void setSex(String inSex) {
        this.SEX = inSex;
    }

    public void setTitle(String inTitle) {
        this.TITLE = inTitle;
    }

    public void setBirthDetails(String inId) {
        this.BIRTH_DETAILS = new HashMap<>();
    }

    public void setFamc(String inFamc) {
        this.FAMC = inFamc;
    }

    public void setFams(String inFams) {
        this.FAMS = inFams;
    }

    public void setCham(String inChan) {
        this.CHAN = inChan;
    }


    /**
     * Getters
     */
    public String getId() {
        return ID;
    }

    public String getName() {
        return this.NAME;
    }

    public String getSex() {
        return this.SEX;
    }

    public String getTitle() {
        return this.TITLE;
    }

    public HashMap<String, String> getBirthDetails() {
        return BIRTH_DETAILS;
    }

    public String getFamc() {
        return this.FAMC;
    }

    public String getFams() {
        return this.FAMS;
    }

    public String getCham() {
        return this.CHAN;
    }

    /**
     * => Extract Record ID
     *
     * @param input
     * @return
     */
    public static String extractId(String input) {
        Integer ID = Integer.parseInt(input.substring(2, input.length() - 1));
        return String.format("%01d", ID);
    }

    /**
     * => Get sub records from multi line record set
     *
     * @param input
     * @return
     */
    private static String[] getSubRecordDetails(String input) {
        return input.split("\r\n|\n|\r");
    }
}

package com.aconex.binders;

import java.util.HashMap;

/**
 * Created by mukthar.ahmed on 5/29/16.
 */
public class XMLEntry {

    RawEntryRecord rawEntryRecord;

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
            rawEntryRecord = new RawEntryRecord(line);

            if (rawEntryRecord.recordKey.startsWith("@I")) {
                this.ID = rawEntryRecord.recordKey;

                System.out.print(
                    "<" + rawEntryRecord.recordValue.toLowerCase()
                        + " id=\"@I" + this.ID + "\">\n"
                );

            }

            /** Using switch to make the code look clean */
            switch (rawEntryRecord.recordKey.toLowerCase()) {
                case "name":
                    setName();
                    break;

                case "sex":
                    setSex();
                    break;

                case "occu":
                    setOccupation();
                    break;

                case "title":
                    setTitle();
                    break;

                case "fams":
                    setFams();
                    break;

                case "famc":
                    setFamc();
                    break;

                case "note":
                    setNote();
                    break;

                case "birt":
                    setBirthDetails();
                    break;

                case "date":
                    setDate();
                    break;

                case "plac":
                    setPlace();
                    break;

                case "chan":
                    setChan();
                    break;


            }

            if (rawEntryRecord.recordKey.equalsIgnoreCase("birt")
                || rawEntryRecord.recordKey.equalsIgnoreCase("chan")
                || rawEntryRecord.recordKey.equalsIgnoreCase("date")
                || rawEntryRecord.recordKey.equalsIgnoreCase("plac")) {

                finalLineItem += line + "\n";

            } else {
//                if (!finalLineItem.isEmpty()) {
//                    OUT_XML.append("\n" + RecordProcessor.process(finalLineItem));
//                    finalLineItem = "";
//                }
//                if (!line.isEmpty()) {
//                    OUT_XML.append("\n" + RecordProcessor.process(line));
//                }

            }

        }

        System.out.print("</indi>\n");

    }


    public void setId(String inId) {
        this.ID = (
            "<" + rawEntryRecord.recordValue.toLowerCase() + " id=\"@I" + extractId(rawEntryRecord.recordKey) + "@\">\n");
    }

    private void setNote() {
        System.out.print(rawEntryRecord.totalTabs + "<note>" + rawEntryRecord.recordValue + "</note>\n");
    }

    public void setName() {
        System.out.print(rawEntryRecord.totalTabs + "<name value=\"" + rawEntryRecord.recordValue + ">\n");
        System.out.print(rawEntryRecord.totalTabs + "\t<surn>\"" + RecordProcessor.extractSirName(rawEntryRecord.recordValue) + "</surn>\n");
        System.out.print(rawEntryRecord.totalTabs + "\t<givn>\"" + RecordProcessor.extractName(rawEntryRecord.recordValue) + "</givn>\n");
        System.out.print(rawEntryRecord.totalTabs + "</name" + ">\n");

    }

    public void setSex() {
        System.out.print(rawEntryRecord.totalTabs + "<sex>" + this.rawEntryRecord.recordValue + "<sex>\n");
    }

    public void setOccupation() {
        System.out.print(rawEntryRecord.totalTabs + "<occu>" + rawEntryRecord.recordValue + "</occu>\n");
    }

    public void setTitle() {
        System.out.print(rawEntryRecord.totalTabs + "<title>" + rawEntryRecord.recordValue + "</title>\n");
    }

    public void setBirthDetails() {
        this.BIRTH_DETAILS = new HashMap<>();
        System.out.print(rawEntryRecord.totalTabs + "<birth>\n");
    }

    public void setPlace() {
        System.out.print(rawEntryRecord.totalTabs + "<place>" + rawEntryRecord.recordValue + "</place>\n");
    }

    public void setChan(){
        System.out.print(rawEntryRecord.totalTabs + "<chan>\n");
    }

    public void setDate() {
        System.out.print(rawEntryRecord.totalTabs + "<date>" + rawEntryRecord.recordValue + "</date>\n");
    }
    public void setFamc() {
        System.out.print(rawEntryRecord.totalTabs + "<famc>" + rawEntryRecord.recordValue + "</famc>\n");
    }

    public void setFams() {
        System.out.print(rawEntryRecord.totalTabs + "<fams>" + rawEntryRecord.recordValue + "</fams>\n");
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

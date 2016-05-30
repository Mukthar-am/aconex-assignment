package com.aconex.binders;


/**
 * Created by mukthar.ahmed on 5/29/16.
 *
 *  Generating xml entry object
 */
public class XMLEntry {
    private RawEntryRecord rawEntryRecord;
    private String ID;
    private StringBuilder XML_TAG = new StringBuilder();

    /** Constructor - with entire parsing logic */
    public XMLEntry(String xmlLineSet) {
        String finalLineItem = "";
        String[] tagLines = getSubRecordDetails(xmlLineSet);

        for (int i = 0; i < tagLines.length; i++) {
            String line = tagLines[i];

            rawEntryRecord = new RawEntryRecord(line);

            if (rawEntryRecord.recordKey.startsWith("@I")) {
                setId(rawEntryRecord);
            }

            if (rawEntryRecord.recordKey.equalsIgnoreCase("birt")
                || rawEntryRecord.recordKey.equalsIgnoreCase("chan")
                || rawEntryRecord.recordKey.equalsIgnoreCase("date")
                || rawEntryRecord.recordKey.equalsIgnoreCase("plac")) {


                finalLineItem += line + "\n";

                if (i == tagLines.length - 1) {
                    if (!finalLineItem.isEmpty()) {
                        if (finalLineItem.toLowerCase().contains("chan")) {
                            setChan(finalLineItem);
                        }
                    }
                }

                continue;   /** jump to the next line */
            }


            if (!finalLineItem.isEmpty()) {
                if (finalLineItem.toLowerCase().contains("birt")) {
                    setBirthDetails(finalLineItem);
                }

                if (finalLineItem.toLowerCase().contains("chan")) {
                    setChan(finalLineItem);
                }
            }

            finalLineItem = "";

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
            }
        }

        XML_TAG.append("\t</indi>\n");

    }


    public void setId(RawEntryRecord entryRecord) {
        this.ID = RecordProcessor.extractId(entryRecord.recordKey);
        XML_TAG.append(
            "\t<" + rawEntryRecord.recordValue.toLowerCase()
                + " id=\"@I" + this.ID + "@\">\n"
        );
    }

    private void setNote() {
        XML_TAG.append(rawEntryRecord.tabSpacedString + "<note>" + rawEntryRecord.recordValue + "</note>\n");
    }

    public void setName() {
        XML_TAG.append(rawEntryRecord.tabSpacedString + "<name value=\"" + rawEntryRecord.recordValue + ">\n");
        XML_TAG.append(rawEntryRecord.tabSpacedString + "\t<surn>\"" + RecordProcessor.extractSirName(rawEntryRecord.recordValue) + "</surn>\n");
        XML_TAG.append(rawEntryRecord.tabSpacedString + "\t<givn>\"" + RecordProcessor.extractName(rawEntryRecord.recordValue) + "</givn>\n");
        XML_TAG.append(rawEntryRecord.tabSpacedString + "</name" + ">\n");

    }

    public void setSex() {
        XML_TAG.append(rawEntryRecord.tabSpacedString + "<sex>" + this.rawEntryRecord.recordValue + "<sex>\n");
    }

    public void setOccupation() {
        XML_TAG.append(rawEntryRecord.tabSpacedString + "<occu>" + rawEntryRecord.recordValue + "</occu>\n");
    }

    public void setTitle() {
        XML_TAG.append(rawEntryRecord.tabSpacedString + "<title>" + rawEntryRecord.recordValue + "</title>\n");
    }

    private void setBirthDetails(String birthDetails) {

        String[] birthDetailItems = birthDetails.split("\\r|\\r\\n|\\n");

        RawEntryRecord lowLevelBirthData = new RawEntryRecord(birthDetailItems[0]);
        XML_TAG.append(lowLevelBirthData.tabSpacedString + "<birth>\n");

        for (String birthItem : birthDetailItems) {
            String[] birthTokens = birthItem.split("\\s");

            if (birthTokens[1].equalsIgnoreCase("date")) {
                setDate(birthItem);
            }
            else if (birthTokens[1].equalsIgnoreCase("plac")) {
                setPlace(birthItem);
            }
        }
        XML_TAG.append(lowLevelBirthData.tabSpacedString + "</birth>\n");
    }

    private void setPlace(String inputLine) {
        RawEntryRecord lowLevelPlaceData = new RawEntryRecord(inputLine);
        XML_TAG.append(lowLevelPlaceData.tabSpacedString + "<place>" + lowLevelPlaceData.recordValue + "</place>\n");
    }

    private void setChan(String chanDetails) {
        String[] chanDetailItems = chanDetails.split("\\r|\\r\\n|\\n");

        RawEntryRecord lowLevelChanData = new RawEntryRecord(chanDetailItems[0]);

        XML_TAG.append(lowLevelChanData.tabSpacedString + "<chan>\n");
        setDate(chanDetailItems[1]);
        XML_TAG.append(lowLevelChanData.tabSpacedString + "</chan>\n");
    }

    public void setDate(String inputLine) {
        RawEntryRecord lowLevelDateRecord = new RawEntryRecord(inputLine);
        XML_TAG.append(lowLevelDateRecord.tabSpacedString + "<date>" + lowLevelDateRecord.recordValue + "</date>\n");
    }

    public void setFamc() {
        XML_TAG.append(rawEntryRecord.tabSpacedString + "<famc>" + rawEntryRecord.recordValue + "</famc>\n");
    }

    public void setFams() {
        XML_TAG.append(rawEntryRecord.tabSpacedString + "<fams>" + rawEntryRecord.recordValue + "</fams>\n");
    }


    /**
     * Getters
     */
    public String xmlTag() {
        return this.XML_TAG.toString();
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

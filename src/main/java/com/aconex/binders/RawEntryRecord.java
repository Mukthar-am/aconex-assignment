package com.aconex.binders;

/**
 * Created by mukthar.ahmed on 5/30/16.
 */
public class RawEntryRecord {

    public Integer tabSpaces;
    public String[] lineItems;
    public String recordKey;
    public String recordValue;
    public String totalTabs;

    public RawEntryRecord(String line) {
        this.lineItems = line.split("\\s");
        this.tabSpaces = Integer.parseInt(lineItems[0]);
        this.recordKey = lineItems[1];

        StringBuffer sb = new StringBuffer();
        for (int i = 2; i < lineItems.length; i++) {
            sb.append(lineItems[i]);
        }

        this.recordValue = sb.toString();
        this.totalTabs = getTabbedString(tabSpaces);  /** Get tab spaced string */
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
}

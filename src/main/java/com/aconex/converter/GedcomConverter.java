package com.aconex.converter;

import com.aconex.binders.AconexConverter;
import com.aconex.binders.XMLEntry;

import java.io.*;

/**
 * Text file to XML AconexConverter
 */
public class GedcomConverter implements AconexConverter {

    private String INPUT_FILE;
    /**
     * Input file to parse
     */
    private StringBuffer OUT_XML = new StringBuffer();

    /**
     * thread safe writer
     */

    public GedcomConverter(String input) {
        this.INPUT_FILE = input;
    }

    public boolean convert() throws FileNotFoundException {
        return doXML();
    }


    /**
     * Initializer, throwing FileNotFoundException if input does not exists else continue
     */
    private void initXML() {
        OUT_XML.append("<gedcom>\n");
    }

    private void closeXML() {
        OUT_XML.append("</gedcom>");
    }

    public boolean doXML() throws FileNotFoundException {
        if (!new File(INPUT_FILE).exists()) {
            throw new FileNotFoundException("# Input file \"" + INPUT_FILE + "\" NOT found");
        }

        boolean isSuccessful = false;

        initXML();      /** Initialize xml parent tags */

        BufferedReader bufferedReader = null;
        String line, finalLineItem, previousId, currentId;
        finalLineItem = previousId = currentId = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(new File(INPUT_FILE)));

            finalLineItem = "";
            while ((line = bufferedReader.readLine()) != null) {

                String[] lineSplitItems = getLineSplits(line);

                if (Integer.parseInt(lineSplitItems[0]) == 0) {
                    previousId = currentId;
                    currentId = lineSplitItems[1];

                    if (!finalLineItem.isEmpty() && previousId != currentId) {
                        OUT_XML.append(new XMLEntry(finalLineItem).xmlTag());
                        finalLineItem = "";
                    }
                }

                finalLineItem += line + "\n";
            }


        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                bufferedReader.close();     /** Close the bufferred reader object */
                OUT_XML.append(new XMLEntry(finalLineItem).xmlTag());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        closeXML();     /** Close xml file object */

        System.out.println(OUT_XML.toString());
        return isSuccessful;
    }


    private static String[] getLineSplits(String input) {
        return input.split("\\s");
    }


    public static void main(String[] args) {
        String inFile = "/Users/mukthar.ahmed/Downloads/aconex-coding-challenge/GEDCOM_Parser_Coding_Challenge/limited_data_set.txt";
        GedcomConverter converter = new GedcomConverter(inFile);
        try {
            converter.doXML();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

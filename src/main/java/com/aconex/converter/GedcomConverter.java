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
        OUT_XML.append("<gedcom>");
        System.out.println("<gedcom>");
    }

    private void closeXML() {
        OUT_XML.append("\n</gedcom>");
        System.out.println("\n</gedcom>");

    }

    public boolean doXML() throws FileNotFoundException {
        if (!new File(INPUT_FILE).exists()) {
            throw new FileNotFoundException("# Input file \"" + INPUT_FILE + "\" NOT found");
        }

        boolean isSuccessful = false;

        initXML();      /** Initialize xml parent tags */

        BufferedReader bufferedReader = null;
        String line, finalLineItem, previousId, currentId;
        line = finalLineItem = previousId = currentId = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(new File(INPUT_FILE)));

            finalLineItem = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineSplitItems = getLineSplits(line);

                if (Integer.parseInt(lineSplitItems[0]) == 0) {
                    previousId = currentId;
                    currentId = lineSplitItems[1];

                    if (previousId != currentId && finalLineItem != null) {
                        XMLEntry xmlEntry = new XMLEntry(finalLineItem);

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

//                /** Case: where last line can be either birth or chan */
//                if (!finalLineItem.isEmpty()) {
//                    OUT_XML.append("\n" + RecordProcessor.process(finalLineItem));
//                }
//                if (line != null) {
//                    OUT_XML.append("\n" + RecordProcessor.process(line));
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        closeXML();     /** Close xml file object */

        //System.out.println(OUT_XML.toString());

        return isSuccessful;
    }


    private static String[] getLineSplits(String input) {
        return input.split("\\s");
    }


    private static boolean lineAppendRequest(String input) {
        String[] lineItems = input.split("\\s");

        return (
            lineItems[1].equalsIgnoreCase("birt")
                || lineItems[1].equalsIgnoreCase("chan")
                || Integer.parseInt(lineItems[0]) >= 2
        );
    }

    /**
     * Check if the flow has to wait for next line
     */
    private boolean waitForNext(String line) {
        String[] lineItems = line.split("\\s");
        return (Integer.parseInt(lineItems[0]) == 2);
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

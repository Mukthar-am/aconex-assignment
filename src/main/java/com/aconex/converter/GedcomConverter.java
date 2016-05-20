package com.aconex.converter;

import com.aconex.binders.AconexConverter;

import java.io.File;
import java.io.FileNotFoundException;

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
    }

    private void closeXML() {
        OUT_XML.append("</gedcom>");
    }

    public boolean doXML() throws FileNotFoundException {
        if (!new File(INPUT_FILE).exists()) {
            throw new FileNotFoundException("# Input file \"" + INPUT_FILE + "\" NOT found");
        }
        boolean isSuccessful = false;

        initXML();


        closeXML();     /** Close xml file object */

        return isSuccessful;
    }
}

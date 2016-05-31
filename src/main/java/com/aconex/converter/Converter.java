package com.aconex.converter;

/**
 * Created by mukthar.ahmed on 5/31/16.
 */
public class Converter {

    public static void main(String[] args) {
        String inFile = args[0];
        String outFile = args[1];

        try {
            new GedcomConverter(inFile, outFile).convert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

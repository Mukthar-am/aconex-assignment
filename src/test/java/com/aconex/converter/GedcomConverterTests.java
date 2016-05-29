package com.aconex.converter;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

/**
 * Unit test for simple GedcomConverter.
 */
public class GedcomConverterTests {

    @Test(groups = "converter-tests", expectedExceptions = FileNotFoundException.class)
    public void TestInputFileDoesNotExists() throws FileNotFoundException {
        System.out.println("# Input file does not exists.");
        String invalidInput = "/Users/mukthar.ahmed/Downloads/input.txt";

        boolean converter = new GedcomConverter(invalidInput).convert();
        System.out.println("# === " + converter);
        Assert.assertTrue(false);
    }

    @Test(groups = "converter-tests")
    public void TestInputFileNotReadable() {
        String nonReadableInput = "/Users/mukthar.ahmed/Data/git/personal/aconex-assignment/aconex-xml-util/non-readable.txt";
        System.out.println("# Input file is not readable for the user.");

        try {
            boolean converterStatus = new GedcomConverter(nonReadableInput).convert();
            System.out.println("# Return value: " + converterStatus);
            Assert.assertFalse(converterStatus);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

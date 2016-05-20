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
        String invalidInput = "~/input.txt";

        boolean converter = new GedcomConverter(invalidInput).convert();

    }

    @Test(groups = "converter-tests")
    public void TestInputFileNotReadable() {
        String nonReadableInput = "/Users/mukthar.ahmed/Downloads/" +
            "aconex-coding-challenge/GEDCOM_Parser_Coding_Challenge/non-readable.txt";
        System.out.println("# Input file is not readable for the user.");
    }
}

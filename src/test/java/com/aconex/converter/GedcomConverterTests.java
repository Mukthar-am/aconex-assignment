package com.aconex.converter;

import com.aconex.utils.Utilities;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Unit test for simple GedcomConverter.
 */
public class GedcomConverterTests {
    private static String baseLineDir = "baseline/";
    private static String outputDir = "output/";
    private static File resourcesDirectory = new File("src/test/resources");
    private static String baseLineDirPath = resourcesDirectory.getAbsolutePath() + "/" + baseLineDir;
    private static String outputDirPath = resourcesDirectory.getAbsolutePath() + "/" + outputDir;

    @Test(groups = "converter-tests", expectedExceptions = FileNotFoundException.class)
    public void TestInputFileDoesNotExists() throws FileNotFoundException {
        System.out.println("# Input file does not exists.");

        String outfile = "/Users/mukthar.ahmed/Downloads/output/outfile.txt";
        String invalidInput = "/Users/mukthar.ahmed/Downloads/input.txt";

        boolean converter = false;
        try {
            converter = new GedcomConverter(invalidInput, outfile).convert();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("# === " + converter);
        Assert.assertTrue(false);
    }

    @Test(groups = "converter-tests", enabled = true)
    public void TestInputFileNotReadable() {
        String outfile = "/Users/mukthar.ahmed/Downloads/output/outfile.txt";
        String nonReadableInput = "/Users/mukthar.ahmed/Data/git/personal/aconex-assignment/aconex-xml-util/non-readable.txt";
        System.out.println("# Input file is not readable for the user.");

        try {
            boolean converterStatus = new GedcomConverter(nonReadableInput, outfile).convert();
            System.out.println("# Return value: " + converterStatus);
            Assert.assertFalse(converterStatus);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(groups = "converter-tests", expectedExceptions = Exception.class)
    public void TestEmptyInputFile() {
        String outfile = "/Users/mukthar.ahmed/Downloads/output/outfile.txt";
        String validInput = "com/acconex/test-data/empty.txt";


        String filepath = this.getClass().getClassLoader().getResource(validInput).getFile().toString();
        System.out.println("# Input file is not readable for the user = " + filepath);

        try {
            boolean converterStatus = new GedcomConverter(filepath, outfile).convert();
            System.out.println("# Return value: " + converterStatus);
            Assert.assertFalse(converterStatus);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test(groups = "converter-tests")
    public void TestSingleRecordInputFile() {

        String validInput = "com/acconex/test-data/single_record_set.txt";
        String outfile = outputDirPath + "outfile2.txt";
        String baseLine = baseLineDirPath + "baseline2.txt";

        String filepath = this.getClass().getClassLoader().getResource(validInput).getFile().toString();
        System.out.println("# Input file = " + filepath);

        try {
            boolean converterStatus = new GedcomConverter(filepath, outfile).convert();
            Assert.assertTrue(Utilities.equalFiles(baseLine, outfile));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(groups = "converter-tests")
    public void TestMultiRecordInputFile() {
        String validInput = "com/acconex/test-data/multi_record_set.txt";
        String outfile = outputDirPath + "outfile3.txt";
        String baseLine = baseLineDirPath + "baseline3.txt";

        String filepath = this.getClass().getClassLoader().getResource(validInput).getFile().toString();
        System.out.println("# Input file = " + filepath);

        try {
            boolean converterStatus = new GedcomConverter(filepath, outfile).convert();
            Assert.assertTrue(Utilities.equalFiles(baseLine, outfile));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test(groups = "converter-tests")
    public void TestNoBirthPlaceRecordInputFile() {
        String validInput = "com/acconex/test-data/no_birth_place_record_set.txt";
        String outfile = outputDirPath + "outfile4.txt";
        String baseLine = baseLineDirPath + "baseline4.txt";

        String filepath = this.getClass().getClassLoader().getResource(validInput).getFile().toString();
        System.out.println("# Input file = " + filepath);

        try {
            boolean converterStatus = new GedcomConverter(filepath, outfile).convert();
            Assert.assertTrue(Utilities.equalFiles(baseLine, outfile));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

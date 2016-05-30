package com.aconex.parser;

import com.aconex.binders.RecordProcessor;
import com.aconex.binders.XMLEntry;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by mukthar.ahmed on 5/29/16.
 *
 *  Record parser - test cases
 */
public class RecordProcessorTests {

    @Test
    public void TestExtractIdValid3DigitId() {
        String baseLine = "110";
        String input = "@I0110@";
        String output = RecordProcessor.extractId(input);
        System.out.println("BaseLine=" + baseLine + ", Output=" + output);
        Assert.assertEquals(baseLine, output);
    }

    @Test
    public void TestExtractIdSingleDigit() {
        String baseLine = "1";
        String input = "@I0001@";
        String output = RecordProcessor.extractId(input);
        System.out.println("BaseLine=" + baseLine + ", Output=" + output);
        Assert.assertEquals(baseLine, output);
    }

    @Test
    public void TestExtractIdFourDigitId() {
        String baseLine = "1234";
        String input = "@I1234@";
        String output = RecordProcessor.extractId(input);
        System.out.println("BaseLine=" + baseLine + ", Output=" + output);
        Assert.assertEquals(baseLine, output);
    }

    @Test
    public void TestExtractSirNameValid() {
        String baseLine = "Windsor";
        String input = "Elizabeth Alexandra Mary /Windsor/";
        String output = RecordProcessor.extractSirName(input);

        System.out.println("BaseLine=" + baseLine + ", Output=" + output);
        Assert.assertEquals(baseLine, output);
    }

    @Test
    public void TestExtractNullSirName() {
        String baseLine = null;
        String input = "Elizabeth Alexandra Mary";
        String output = RecordProcessor.extractSirName(input);

        System.out.println("BaseLine=" + baseLine + ", Output=" + output);
        Assert.assertEquals(baseLine, output);
    }

    @Test
    public void TestExtractLongSirName() {
        String baseLine = "Windor Manner";
        String input = "Elizabeth Alexandra Mary /Windor Manner/";
        String output = RecordProcessor.extractSirName(input);

        System.out.println("BaseLine=" + baseLine + ", Output=" + output);
        Assert.assertEquals(baseLine, output);
    }


    @Test
    public void TestFullRecordExtracted() {
        String baseLine = "\t<indi id=\"@I1@\">\n" +
            "\t\t<name value=\"Elizabeth Alexandra Mary /Windsor/>\n" +
            "\t\t\t<surn>\"Windsor</surn>\n" +
            "\t\t\t<givn>\"Elizabeth Alexandra Mary </givn>\n" +
            "\t\t</name>\n" +
            "\t\t<sex>F<sex>\n" +
            "\t\t<birth>\n" +
            "\t\t\t<date>21 Apr 1926</date>\n" +
            "\t\t\t<place>17 Bruton Street, London, W1</place>\n" +
            "\t\t</birth>\n" +
            "\t\t<occu>Queen</occu>\n" +
            "\t\t<famc>@F0003@</famc>\n" +
            "\t\t<fams>@F0001@</fams>\n" +
            "\t\t<note>@N0002@</note>\n" +
            "\t\t<chan>\n" +
            "\t\t\t<date>13 Dec 2003</date>\n" +
            "\t\t</chan>\n" +
            "\t</indi>\n";

        String input = "0 @I0001@ INDI\n" +
            "1 NAME Elizabeth Alexandra Mary /Windsor/\n" +
            "1 SEX F\n" +
            "1 BIRT\n" +
            "2 DATE 21 Apr 1926\n" +
            "2 PLAC 17 Bruton Street, London, W1\n" +
            "1 OCCU Queen\n" +
            "1 FAMC @F0003@\n" +
            "1 FAMS @F0001@\n" +
            "1 NOTE @N0002@\n" +
            "1 CHAN\n" +
            "2 DATE 13 Dec 2003";

        String output = new XMLEntry(input).xmlTag();
        Assert.assertEquals(output, baseLine);
    }


    @Test
    public void TestBirthAndChanValuesExtracted() {
        Object baseLine = new StringBuilder("\t<indi id=\"@I1@\">\n" +
            "\t\t<name value=\"Elizabeth Alexandra Mary /Windsor/>\n" +
            "\t\t\t<surn>\"Windsor</surn>\n" +
            "\t\t\t<givn>\"Elizabeth Alexandra Mary </givn>\n" +
            "\t\t</name>\n" +
            "\t\t<sex>F<sex>\n" +
            "\t\t<birth>\n" +
            "\t\t\t<date>21 Apr 1926</date>\n" +
            "\t\t</birth>\n" +
            "\t\t<occu>Queen</occu>\n" +
            "\t\t<famc>@F0003@</famc>\n" +
            "\t\t<fams>@F0001@</fams>\n" +
            "\t\t<note>@N0002@</note>\n" +
            "\t\t<chan>\n" +
            "\t\t\t<date>13 Dec 2003</date>\n" +
            "\t\t</chan>\n" +
            "\t</indi>\n").toString();

        String input = "0 @I0001@ INDI\n" +
            "1 NAME Elizabeth Alexandra Mary /Windsor/\n" +
            "1 SEX F\n" +
            "1 BIRT\n" +
            "2 DATE 21 Apr 1926\n" +
            "1 OCCU Queen\n" +
            "1 FAMC @F0003@\n" +
            "1 FAMS @F0001@\n" +
            "1 NOTE @N0002@\n" +
            "1 CHAN\n" +
            "2 DATE 13 Dec 2003";

        Object output = new XMLEntry(input).xmlTag();
        Assert.assertEquals(output, baseLine);
    }
}

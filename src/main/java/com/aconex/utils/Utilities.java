package com.aconex.utils;

import java.io.*;

/**
 * Created by mukthar.ahmed on 5/31/16.
 *
 *  Generic utilities
 */
public class Utilities {

    public static void writeToFile(String fileContent, String outfilePath) {
        System.out.println("# Deleting file = " + outfilePath);
        File outfile = new File(outfilePath);
        if (outfile.exists()) {
            outfile.delete();
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outfilePath)));
            bw.write(fileContent);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean equalFiles(String expectedFileName,
                                      String resultFileName) {
        boolean equal;
        BufferedReader bExp;
        BufferedReader bRes;
        String expLine ;
        String resLine ;

        equal = false;
        bExp = null ;
        bRes = null ;

        try {
            bExp = new BufferedReader(new FileReader(expectedFileName));
            bRes = new BufferedReader(new FileReader(resultFileName));

            if ((bExp != null) && (bRes != null)) {
                expLine = bExp.readLine() ;
                resLine = bRes.readLine() ;

                equal = ((expLine == null) && (resLine == null)) || ((expLine != null) && expLine.equals(resLine)) ;

                while(equal && expLine != null)
                {
                    expLine = bExp.readLine() ;
                    resLine = bRes.readLine() ;
                    equal = expLine.equals(resLine) ;
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                if (bExp != null) {
                    bExp.close();
                }
                if (bRes != null) {
                    bRes.close();
                }
            } catch (Exception e) {
            }

        }

        return equal;

    }


    public static void main(String[] args) {
        String outfile = "/Users/mukthar.ahmed/Downloads/output/outfile.txt";
        String baseLine = "/Users/mukthar.ahmed/Downloads/output/outfile2.txt";

        System.out.println("Files Match = " + equalFiles(outfile, baseLine) );
    }

}

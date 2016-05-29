//package com.aconex.utilities;
//
//import java.io.*;
//
///**
// * Created by mukthar.ahmed on 5/27/16.
// *
// *  Gedcom input data loader
// */
//public class GedcomDataLoader {
//    private String IN_FILE;
//
//    public GedcomDataLoader(String inputFile) {
//        this.IN_FILE = inputFile;
//
//    }
//
//    public void load() {
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(IN_FILE)));
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
//    }
//
//    public static void main(String[] args) {
//        String inFile = "/Users/mukthar.ahmed/Downloads/aconex-coding-challenge/GEDCOM_Parser_Coding_Challenge/limited_data_set.txt";
//        GedcomDataLoader loader = new GedcomDataLoader(inFile);
//        loader.load();
//    }
//}

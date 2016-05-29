package com.aconex.binders;

import java.util.HashMap;

/**
 * Created by mukthar.ahmed on 5/29/16.
 */
public class Entry {

    private int ID;
    private String NAME;
    private String SEX;
    private HashMap<String, String> BIRTH_DETAILS;
    private String TITLE;
    private String FAMC;
    private String FAMS;
    private String CHAN;

    public Entry() {
    }

    public void setId(String inId) { this.ID = Integer.parseInt(inId); }
    public void setName(String inName) { this.NAME = inName; }

    public void setSex(String inSex) { this.SEX = inSex; }

    public void setTitle(String inTitle) { this.TITLE = inTitle; }
    public void setBirthDetails(String inId) {
        this.BIRTH_DETAILS = new HashMap<>();
    }
    public void setFamc(String inFamc) { this.FAMC = inFamc; }
    public void setFams(String inFams) { this.FAMS = inFams; }
    public void setCham(String inChan) { this.CHAN = inChan; }


    /** Getters */
    public Integer getId() { return ID; }
    public String getName() { return this.NAME;}

    public String getSex() { return this.SEX; }

    public String getTitle() { return this.TITLE; }
    public HashMap<String, String> getBirthDetails() {
        return BIRTH_DETAILS;
    }
    public String getFamc() { return this.FAMC; }
    public String getFams() { return this.FAMS; }
    public String getCham() { return this.CHAN; }
}

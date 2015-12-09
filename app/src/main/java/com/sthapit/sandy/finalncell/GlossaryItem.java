package com.sthapit.sandy.finalncell;

/**
 * Created by Dell on 12/8/2015.
 */
public class GlossaryItem {
    private String name_nepali;
    private String name;


    public GlossaryItem() {

    }

    public GlossaryItem(String i, String d, String p, int id) {
        this.name_nepali = d;
        this.name = i;
    }

    public String getName_nepali() {
        return name_nepali;
    }

    public void setName_nepali(String details) {
        this.name_nepali = details;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

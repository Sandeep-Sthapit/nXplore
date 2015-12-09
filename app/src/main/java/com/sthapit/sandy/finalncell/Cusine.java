package com.sthapit.sandy.finalncell;

import java.util.Vector;

/**
 * Created by Dell on 11/21/2015.
 */
public class Cusine {


    private Vector<Items> CusineItem;

    public Items getCusineItem(int i) {
        return CusineItem.get(i);
    }

    public void setCusineItem(Items items, int i) {
        this.CusineItem.set(i, items);
    }
}


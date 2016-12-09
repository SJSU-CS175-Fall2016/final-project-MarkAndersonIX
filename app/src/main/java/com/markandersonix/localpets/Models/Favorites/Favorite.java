package com.markandersonix.localpets.Models.Favorites;

/**
 * Created by Mark on 10/9/2016.
 */

public class Favorite {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private String id;
    private int index;

}

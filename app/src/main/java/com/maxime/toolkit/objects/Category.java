package com.maxime.toolkit.objects;

/**
 * Created by Maxime on 2017-10-04.
 */

public class Category {

    int    id;
    String name;
    boolean selected;

    /*********************************** Constructor ************************************/

    public Category(int _id, String _name, boolean _selected) {
        this.id = _id;
        this.name = _name;
        this.selected = _selected;
    }

    /*************************************** GETTER *******************************************/

    public int getID() { return this.id; }

    public String getName() {
        return this.name;
    }

    public boolean isSelected() { return this.selected; }

    /*************************************** SETTER *******************************************/

    public void setSelected(Boolean isSelected) { selected = isSelected; }

}

package com.maxime.toolkit.objects;

/**
 * Created by Maxime on 2017-10-22.
 */

public class User
{
    private static final User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }

    /*public static User getInstance() {
        if(ourInstance == null)
            ourInstance = new User();

        return ourInstance;
    }*/
}

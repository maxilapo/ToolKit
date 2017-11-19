package com.maxime.toolkit.objects;

/**
 * Created by Maxime on 2017-10-04.
 */

public class Evaluation {

    String name;
    int score;
    String comment;

    /*********************************** Constructor ************************************/

    public Evaluation(String _name, int _score, String _comment) {
        this.name = _name;
        this.score = _score;
        this.comment = _comment;
    }

    /*************************************** GETTER *******************************************/

    public String getName() {
        return this.name;
    }

    public int getScore() { return this.score; }

    public String getComment() {
        return this.comment;
    }

    public String getScoreStars() {

        switch (this.score)
        {
            case 1:
                return  "★";
            case 2:
                return  "★★";
            case 3:
                return  "★★★";
            case 4:
                return  "★★★★";
            case 5:
                return  "★★★★★";
            default:
                return  "N/A";
        }
    }

}

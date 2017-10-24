package com.maxime.toolkit.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maxime on 2017-10-04.
 */

public class Product implements Parcelable{

    int id;
    String title;
    String description;
    String imageURL;
    double price;
    int    rating = 4;

    /*********************************** Constructor ************************************/

    public Product(int _id, String _title, double _price) {
        this.id = _id;
        this.title = _title;
        this.price = _price;
    }

    public Product(int _id, String _title, String _description, double _price) {
        this.id = _id;
        this.title = _title;
        this.description = _description;
        this.price = _price;
    }

    public Product(int _id, String _title, String _description, double _price, String _imageURL) {
        this.id = _id;
        this.title = _title;
        this.description = _description;
        this.price = _price;
        this.imageURL = _imageURL;
    }


    /*************************************** GETTER *******************************************/

    public int getID() { return id; }

    public String getTitle() {
        return title;
    }

    public String getDescription() { return description; }

    public double getPrice() { return price; }

    public String getImageUrl() { return imageURL; }

    public int getRating() { return rating; }

    public String getRatingStar() {

        switch (rating)
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
                return  "No rating";
        }
    }

    /*********************************** Parcelable Stuff ************************************/

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.price = in.readDouble();
        this.imageURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeDouble(this.price);
        dest.writeString(this.imageURL);
    }
}

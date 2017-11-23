package com.maxime.toolkit.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Maxime on 2017-10-04.
 */

public class Delivery {

    int    id;
    double total;
    String state;
    ArrayList<String> listProduit = new ArrayList<>();;
    Client client;

    /*********************************** Constructor ************************************/

    public Delivery(int _id, double _total) {
        this.id = _id;
        this.total = _total;
    }

    /*************************************** GETTER *******************************************/
    public int getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public String getState() {
        return state;
    }

    public ArrayList<String> getListProduit() {
        return listProduit;
    }

    public Client getClient() {
        return client;
    }

    public String formattedProductList(){

        String listName = "";
        for(String pro : listProduit) {
            listName += "test\n";
        }

        return listName;
    }

    /*************************************** SETTER *******************************************/

    public void setId(int id) {
        this.id = id;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setListProduit(ArrayList<String> listProduit) {
        this.listProduit = listProduit;
    }

    public void addProductName(String productName){


        this.listProduit.add(productName);
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

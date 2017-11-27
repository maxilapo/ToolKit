package com.maxime.toolkit.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Maxime on 2017-10-04.
 */

public class SaleOrders {

    int    id;
    double total;
    String state;
    ArrayList<Product> listProduit = new ArrayList<>();;
    Client client;

    /*********************************** Constructor ************************************/

    public SaleOrders(int _id, double _total) {
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

    public Client getClient() {
        return client;
    }

    public String formattedProductList(){

        String listName = "";
        for(Product pro : listProduit) {

            String qty = "";

            if (pro.getQuantity() > 1)
                qty = " (" + pro.getQuantity() + ")";


            listName += "→ " + pro.getTitle() + qty + "\n";
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

    public void addProductName(Product _product){
        listProduit.add(_product);
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

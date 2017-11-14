package com.maxime.toolkit.objects;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Maxime on 2017-10-04.
 */

public class Panier {

    private static final Panier ourInstance = new Panier();

    private ArrayList<Product> productArray = new ArrayList<Product>();

    public static Panier getInstance() {
        return ourInstance;
    }

    public Product[] getCartProduct(){
        Product[] productList = productArray.toArray(new Product[productArray.size()]);
        return productList;
    }

    public void addToCart(Product newProduit) {

        for(Product p : productArray)
        {
            //If the same product, increment quantity
            if(p.getID() != 0 && p.getID() == newProduit.getID()){
                p.incrementQuantity();
                return;
            }
        }
        newProduit.incrementQuantity();
        productArray.add(newProduit);
    }


    public void incrementProduit(int productID) {

        for(Product p : productArray)
        {
            //If the same product, increment quantity
            if(p.getID() != 0 && p.getID() == productID){
                Log.d("max_PANIER", "INCREMENTE");
                p.incrementQuantity();
                return;
            }
        }
    }

    public void decrementProduit(int productID) {

        Log.d("max_PANIER", "DECREMENT");
        for(Product p : productArray)
        {
            if(p.getID() != 0 && p.getID() == productID)
            {
                p.decrementQuantity();

                //If 0 quantity, remove object.
                if (p.getQuantity() <= 0){
                    productArray.remove(p);
                }
                return;
            }
        }
    }

    public double getSubtotal() {

        double subtotal = 0;
        for(Product p : productArray) {
            subtotal += p.getQuantity() * p.getPrice();
        }

        return  subtotal;
    }

    public String getSubtotalFormatted() {

        double subtotal = getSubtotal();

        String formatedTotal = String.format("%.2f$", subtotal);
        return formatedTotal;
    }


    public Product[] getCartProductFAKE() {

        Product tempProduct = new Product(1, "Produit Title Title Title Title Title Title Title Title Title Title Title Title Title Title Title Title",
                "Description lalalalala", 22.50,
                "http://res.cloudinary.com/dbpym5dfx/image/upload/v1508366324/etui_silicone_noir_iphone_6s_ykhbgl.jpg", 2);

        Product tempProduct2 = new Product(2, "Produit Title 2",
                "Description lalalalala 2", 55.25,
                "http://res.cloudinary.com/dbpym5dfx/image/upload/v1508366324/etui_silicone_noir_iphone_7_xdow6k.jpg", 3);


        Product[] productList = {tempProduct, tempProduct2};

        return productList;
    }




}

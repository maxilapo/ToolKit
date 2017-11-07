package com.maxime.toolkit.objects;

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

    public void addToCart(Product produit){
        productArray.add(produit);
    }

    public Product[] getAllProduct(){
        Product[] productList = productArray.toArray(new Product[productArray.size()]);
        return productList;
    }



}

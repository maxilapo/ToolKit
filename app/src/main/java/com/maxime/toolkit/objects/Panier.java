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

    public Product[] getCartProduct(){
        Product[] productList = productArray.toArray(new Product[productArray.size()]);
        return productList;
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

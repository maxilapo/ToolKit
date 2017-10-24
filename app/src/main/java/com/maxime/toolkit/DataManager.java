package com.maxime.toolkit;

import com.maxime.toolkit.objects.Product;

/**
 * Created by Maxime on 2017-10-04.
 */

public class DataManager {

    public static Product[] getAllProducts() {

        Product product1 = new Product(1, "Fucking esti de long long title", "Description1", 22.50, "http://i.imgur.com/zuG2bGQ.jpg");

        Product product2 = new Product(2, "Titre2", "Description2", 55.99, "http://i.imgur.com/ovr0NAF.jpg");

        Product product3 = new Product(3, "Titre3", "Description3", 89.99, "http://i.imgur.com/n6RfJX2.jpg");

        Product product4 = new Product(4, "Titre4", "Description4", 28.99, "http://i.imgur.com/qpr5LR2.jpg");

        Product product5 = new Product(5, "Titre5 Titre5 Titre5 Titre5 Titre5", "Description5", 49.99, "http://i.imgur.com/qpr5LR2.jpg");

        Product product6 = new Product(6, "Titre6", "Description6", 69.99, "http://i.imgur.com/qpr5LR2.jpg");

        Product[] listProduct = {product1, product2, product3, product4, product5, product6};

        return listProduct;
    }
}

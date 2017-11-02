package com.maxime.toolkit;

import android.util.Log;

import com.maxime.toolkit.objects.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;

/**
 * Created by Maxime on 2017-10-04.
 */

public class DataManager {

    private RequestManager _requestManager;

    private OkHttpClient httpClient;
    String port = "6969";
    String GET = "GET";
    String POST = "POST";

    public DataManager() {
        _requestManager = new RequestManager();
    }

    public String getUserName() {

        try {

            String[] jsonFriend = _requestManager.httpRequest(GET, "products");
            //Log.d("max_DataManager", "REQUEST RESULT :::: " + jsonFriend[0].toString());

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //this.initRequestManager();

        return "lla";

    }



    public Product[] getAllProducts() {

        String[] jsonResult = new String[1];

        ArrayList<Product> listProduct = new ArrayList<Product>();

        try {
            jsonResult = _requestManager.httpRequest(GET, "products");


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {

            JSONArray jsonProductList = new JSONArray(jsonResult[0]);


            for(int i=0; i<jsonProductList.length(); i++){

                try {
                    int id = jsonProductList.getJSONObject(i).getInt("id");
                    String name = jsonProductList.getJSONObject(i).getString("name");
                    double list_price = jsonProductList.getJSONObject(i).getDouble("list_price");
                    String description_sale = jsonProductList.getJSONObject(i).getString("description_sale");
                    String description = jsonProductList.getJSONObject(i).getString("description");
                    double rating_last_value = jsonProductList.getJSONObject(i).getDouble("rating_last_value");


                    if (list_price == Double.NaN || description == null || description_sale == null)
                        continue;

                    Product tempProduct = new Product(id, name, description_sale, list_price, description);

                    listProduct.add(tempProduct);
                }
                catch (JSONException e) {

                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
/*
        Product product1 = new Product(1, "Fucking esti de long long title", "Description1", 22.50, "http://i.imgur.com/zuG2bGQ.jpg");

        Product product2 = new Product(2, "Titre2", "Description2", 55.99, "http://i.imgur.com/ovr0NAF.jpg");

        Product product3 = new Product(3, "Titre3", "Description3", 89.99, "http://i.imgur.com/n6RfJX2.jpg");

        Product product4 = new Product(4, "Titre4", "Description4", 28.99, "http://i.imgur.com/qpr5LR2.jpg");

        Product product5 = new Product(5, "Titre5 Titre5 Titre5 Titre5 Titre5", "Description5", 49.99, "http://i.imgur.com/qpr5LR2.jpg");

        Product product6 = new Product(6, "Titre6", "Description6", 69.99, "http://i.imgur.com/qpr5LR2.jpg");

        Product[] listProduct = {product1, product2, product3, product4, product5, product6};*/

        Product[] suluuu = listProduct.toArray(new Product[listProduct.size()]);

        return suluuu;
    }
}

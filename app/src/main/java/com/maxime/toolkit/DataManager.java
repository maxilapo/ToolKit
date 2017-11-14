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

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "lla";
    }

    public Product getProductDetails(int _id) {

        String[] jsonResult = new String[1];
        String url = "product/" + String.valueOf(_id);
        Product produit;

        try {
            jsonResult = _requestManager.httpRequest(GET, url);
        }
        catch (ExecutionException e) { e.printStackTrace();}
        catch (InterruptedException e) { e.printStackTrace();}

        try {
            // Json is in an array, deal with it. 😎
            JSONArray jsonProductList = new JSONArray(jsonResult[0]);
            JSONObject jsonProduct = jsonProductList.getJSONObject(0);

            int id = jsonProduct.getInt("id");
            String name = jsonProduct.getString("name");
            String description_sale = jsonProduct.getString("description_sale");
            String description = jsonProduct.getString("description");
            double list_price = jsonProduct.getDouble("list_price");
            double rating_last_value = jsonProduct.getDouble("rating_last_value");

            return new Product(id, name, description_sale, list_price, description, rating_last_value);
        }
        catch (JSONException e) {e.printStackTrace();}

        return null;
    }

        public Product[] getAllProducts() {

        String[] jsonResult = new String[1];
        ArrayList<Product> productArray = new ArrayList<Product>();

        try {
            jsonResult = _requestManager.httpRequest(GET, "products");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonProductList = new JSONArray(jsonResult[0]);
            for(int i=0; i<jsonProductList.length(); i++)
            {
                try {
                    int id = jsonProductList.getJSONObject(i).getInt("id");
                    String name = jsonProductList.getJSONObject(i).getString("name");
                    String description_sale = jsonProductList.getJSONObject(i).getString("description_sale");
                    String description = jsonProductList.getJSONObject(i).getString("description");
                    double list_price = jsonProductList.getJSONObject(i).getDouble("list_price");
                    double rating_last_value = jsonProductList.getJSONObject(i).getDouble("rating_last_value");

                    if (list_price == Double.NaN || description == null || name == null || description_sale == null)
                        continue;

                    Product tempProduct = new Product(id, name, description_sale, list_price, description, rating_last_value);
                    productArray.add(tempProduct);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        Product[] productList = productArray.toArray(new Product[productArray.size()]);

        return productList;
    }
}

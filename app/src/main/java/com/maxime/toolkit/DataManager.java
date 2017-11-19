package com.maxime.toolkit;

import android.util.Log;

import com.maxime.toolkit.objects.Category;
import com.maxime.toolkit.objects.Evaluation;
import com.maxime.toolkit.objects.Product;
import com.maxime.toolkit.objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;


/**
 * Created by Maxime on 2017-10-04.
 */

public class DataManager {

    private RequestManager _requestManager;



    private String GET = "GET";
    private String POST = "POST";

    public DataManager() {
        _requestManager = new RequestManager();
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

    public Product[] getProducts() {

        ArrayList<Category> tempArray = User.getInstance().getCategoryArray();

        for(Category cat : tempArray)
        {
            if(cat.isSelected() && cat.getID() != 0){
                return getProductsForCategory(cat.getID());
            }
        }

        return getAllProducts();
    }


    public Product[] getProductsForCategory(int categoryID) {

        if (categoryID == 0)
            return  getAllProducts();

        String[] jsonResult = new String[1];
        ArrayList<Product> productArray = new ArrayList<Product>();

        try {
            jsonResult = _requestManager.httpRequest(GET, "products/category/" + categoryID);
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

    public Category[] getAllCategories() {

        String[] jsonResult = new String[1];
        ArrayList<Category> categoryArray = new ArrayList<Category>();

        try {
            jsonResult = _requestManager.httpRequest(GET, "categories");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonCategoryList = new JSONArray(jsonResult[0]);

            Category allProductCategory = new Category(0, "All Products", true);
            categoryArray.add(allProductCategory);

            for(int i=0; i<jsonCategoryList.length(); i++)
            {
                try {
                    int id = jsonCategoryList.getJSONObject(i).getInt("id");
                    String name = jsonCategoryList.getJSONObject(i).getString("name");

                    if (name == null)
                        continue;

                    Category tempCategory = new Category(id, name, false);
                    categoryArray.add(tempCategory);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        // Sorting the array
        Collections.sort(categoryArray, new Comparator<Category>() {
            @Override
            public int compare(Category cat2, Category cat1)
            {

                return  cat2.getName().compareTo(cat1.getName());
            }
        });

        User.getInstance().setCategoryArray(categoryArray);

        Category[] categoryList = categoryArray.toArray(new Category[categoryArray.size()]);

        return categoryList;
    }

    public Evaluation[] getProductEvaluation(int productID) {

        String[] jsonResult = new String[1];
        ArrayList<Evaluation> evaluationArray = new ArrayList<>();

        try {
            jsonResult = _requestManager.httpRequest(GET, "products/" + productID + "/reviews");
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
                    String name = jsonProductList.getJSONObject(i).getString("name");
                    int score = jsonProductList.getJSONObject(i).getInt("score");
                    String comment = jsonProductList.getJSONObject(i).getString("comment");

                    if (name == null)
                        continue;

                    Evaluation tempEval = new Evaluation(name, score, comment);
                    evaluationArray.add(tempEval);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        Evaluation[] evalList = evaluationArray.toArray(new Evaluation[evaluationArray.size()]);

        return evalList;
    }




}

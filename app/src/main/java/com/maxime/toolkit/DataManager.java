package com.maxime.toolkit;

import android.util.Log;

import com.maxime.toolkit.objects.Category;
import com.maxime.toolkit.objects.Client;
import com.maxime.toolkit.objects.SaleOrders;
import com.maxime.toolkit.objects.Evaluation;
import com.maxime.toolkit.objects.Product;
import com.maxime.toolkit.objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;


/**
 * Created by Maxime on 2017-10-04.
 */

public class DataManager {

    private RequestManager _requestManager;

    private final String className = "app_DataManager";
    private final String GET = "GET";
    private final String POST = "POST";

    public DataManager() {
        _requestManager = new RequestManager();
    }

    /********************************** GET *******************************************/
    public Product getProductDetails(int _id) {

        String[] jsonResult = new String[1];
        String url = "product/" + String.valueOf(_id);

        try {
            jsonResult = _requestManager.httpRequest(GET, url, "");
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
            jsonResult = _requestManager.httpRequest(GET, "products/category/" + categoryID, "");
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
            jsonResult = _requestManager.httpRequest(GET, "products", "");
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
            jsonResult = _requestManager.httpRequest(GET, "categories", "");
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
            jsonResult = _requestManager.httpRequest(GET, "products/" + productID + "/reviews", "");
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

    public Client getClient(int _id) {

        String[] jsonResult = new String[1];
        String url = "clients/" + String.valueOf(_id);

        try {
            jsonResult = _requestManager.httpRequest(GET, url, "");
        }
        catch (ExecutionException e) { e.printStackTrace();}
        catch (InterruptedException e) { e.printStackTrace();}

        try {
            // Json is in an array, deal with it. 😎
            JSONArray jsonClientList = new JSONArray(jsonResult[0]);
            JSONObject jsonClient = jsonClientList.getJSONObject(0);

            int id = jsonClient.getInt("id");
            String name = jsonClient.getString("name");
            String email = jsonClient.getString("email");
            String phone = jsonClient.getString("phone");

            String street = jsonClient.getString("street");
            String street2 = jsonClient.getString("street2");
            String city = jsonClient.getString("city");
            String zip = jsonClient.getString("zip");
            String province = jsonClient.getString("barcode");

            return new Client(id, name, email, phone, street, street2, city, zip, province);
        }
        catch (JSONException e) {e.printStackTrace();}

        return null;
    }

    public SaleOrders[] getAllDelivery() throws ExecutionException, InterruptedException, JSONException {
        String[] jsonResult;
        ArrayList<SaleOrders> saleOrdersArray = new ArrayList<>();

        jsonResult = _requestManager.httpRequest(GET, "saleorders/deliveries", "");

        JSONArray jsonDeliveryList = new JSONArray(jsonResult[0]);
        for(int i=0; i<jsonDeliveryList.length(); i++)
        {
            int id = jsonDeliveryList.getJSONObject(i).getInt("id");
            int clientID = jsonDeliveryList.getJSONObject(i).getInt("client_id");
            double total = jsonDeliveryList.getJSONObject(i).getDouble("amount_total");

            if (clientID == 0 || id == 0)
                continue;

            //Create temp delivery object
            SaleOrders tempSaleOrders = new SaleOrders(id, total);
            tempSaleOrders.setClient(getClient(clientID));

            //Find all the product name
            JSONArray jsonItemList = jsonDeliveryList.getJSONObject(i).getJSONArray("items");
            for(int j=0; j<jsonItemList.length(); j++){
                String productName = jsonItemList.getJSONObject(j).getString("name");
                int productQty = jsonItemList.getJSONObject(j).getInt("quantity");
                Product tempProduct = new Product(productName, productQty);
                tempSaleOrders.addProductName(tempProduct);
            }



            saleOrdersArray.add(tempSaleOrders);
        }

        SaleOrders[] saleOrdersList = saleOrdersArray.toArray(new SaleOrders[saleOrdersArray.size()]);

        return saleOrdersList;
    }

    public SaleOrders[] getAllSaleorders() throws ExecutionException, InterruptedException, JSONException {
        String[] jsonResult;
        ArrayList<SaleOrders> saleOrdersArray = new ArrayList<>();

        jsonResult = _requestManager.httpRequest(GET, "saleorders/", "");

        JSONArray jsonDeliveryList = new JSONArray(jsonResult[0]);
        for(int i=0; i<jsonDeliveryList.length(); i++)
        {
            int id = jsonDeliveryList.getJSONObject(i).getInt("id");
            int clientID = jsonDeliveryList.getJSONObject(i).getInt("client_id");
            double total = jsonDeliveryList.getJSONObject(i).getDouble("amount_total");

            if (clientID == 0 || id == 0)
                continue;

            //Create temp delivery object
            SaleOrders tempSaleOrders = new SaleOrders(id, total);
            tempSaleOrders.setClient(getClient(clientID));

            //Find all the product name
            JSONArray jsonItemList = jsonDeliveryList.getJSONObject(i).getJSONArray("items");
            for(int j=0; j<jsonItemList.length(); j++){
                String productName = jsonItemList.getJSONObject(j).getString("name");
                int productQty = jsonItemList.getJSONObject(j).getInt("quantity");
                Product tempProduct = new Product(productName, productQty);
                tempSaleOrders.addProductName(tempProduct);
            }

            saleOrdersArray.add(tempSaleOrders);
        }

        SaleOrders[] saleOrdersList = saleOrdersArray.toArray(new SaleOrders[saleOrdersArray.size()]);

        return saleOrdersList;
    }

    /********************************** POST *******************************************/

    public boolean addEvaluation (int productID, String name, int score, String comment) {

        String[] jsonResult = new String[1];
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("name", name);
            jsonParams.put("score", String.valueOf(score));
            jsonParams.put("comment", comment);
            jsonParams.put("product_id", productID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonResult = _requestManager.httpRequest(POST, "products/" + productID + "/review", jsonParams.toString());
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
                    if(jsonProductList.getJSONObject(i).has("id"))
                        return true;
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Client addClient (String email, String firstname, String lastname, String phone, String adress, String address2,
                             String city, String zip, String province) throws JSONException, ExecutionException, InterruptedException {

        String[] jsonResult = new String[1];
        JSONObject jsonParams = new JSONObject();

        jsonParams.put("email", email);
        jsonParams.put("firstname", firstname);
        jsonParams.put("name", lastname);
        jsonParams.put("address", adress);
        jsonParams.put("city", city);
        jsonParams.put("zip", zip);
        jsonParams.put("country", "Canada");
        jsonParams.put("state", province);
        jsonParams.put("phone", phone);

        Log.d("max_DATAMANAGER", "JSON new client : " +  jsonParams.toString());

        jsonResult = _requestManager.httpRequest(POST, "clients/", jsonParams.toString());

        JSONArray jsonClient = new JSONArray(jsonResult[0]);
        for(int i=0; i<jsonClient.length(); i++)
        {
            if(jsonClient.getJSONObject(i).has("id")){
                int id = jsonClient.getJSONObject(i).getInt("id");
                return getClient(id);
            }
        }
        return null;
    }

    public boolean addSaleOrders (int clientID, double totalPrice, Product[] listProduit) throws JSONException, ExecutionException, InterruptedException {

        String[] jsonResult = new String[1];
        JSONObject jsonParams = new JSONObject();
        JSONArray jsonArrayProduit = new JSONArray();

        jsonParams.put("client_id", clientID);
        jsonParams.put("amount_total", totalPrice);

        for(Product pro : listProduit)
        {
            JSONObject jsonObjectProduit = new JSONObject();
            jsonObjectProduit.put("id", pro.getID());
            jsonObjectProduit.put("name", pro.getTitle());
            jsonObjectProduit.put("list_price", pro.getPrice());
            jsonObjectProduit.put("quantity", pro.getQuantity());

            jsonArrayProduit.put(jsonObjectProduit);
        }

        jsonParams.put("items", jsonArrayProduit);

        Log.d("max_DATAMANAGER", "JSON SALEORDERS : " +  jsonParams.toString());

        jsonResult = _requestManager.httpRequest(POST, "saleorders/", jsonParams.toString());

        JSONArray jsonSale = new JSONArray(jsonResult[0]);
        for(int i=0; i<jsonSale.length(); i++)
        {
            if(jsonSale.getJSONObject(i).has("id")){
                return true;
            }
        }

        return false;
    }

    public boolean addCreditCard (int clientID, String type, BigInteger cardNumber, int month, int year, String holder, int CVV) throws JSONException, ExecutionException, InterruptedException {

        String[] jsonResult = new String[1];
        JSONObject jsonParams = new JSONObject();

        jsonParams.put("client_id", clientID);
        jsonParams.put("type", type);
        jsonParams.put("card_number", cardNumber);
        jsonParams.put("card_month", month);
        jsonParams.put("card_year", year);
        jsonParams.put("card_holder", holder);
        jsonParams.put("cvv", CVV);

        Log.d("max_DATAMANAGER", "JSON creditcard : " +  jsonParams.toString());

        jsonResult = _requestManager.httpRequest(POST, "creditcards/", jsonParams.toString());

        JSONArray jsonCCDone = new JSONArray(jsonResult[0]);
        for(int i=0; i<jsonCCDone.length(); i++)
        {
            if(jsonCCDone.getJSONObject(i).has("id")){
                return true;
            }
        }

        return false;
    }


    public boolean login(String email, String password) throws JSONException, ExecutionException, InterruptedException {

        String[] jsonResult = new String[1];
        JSONObject jsonParams = new JSONObject();

        jsonParams.put("email", email);
        jsonParams.put("password", password);

        jsonResult = _requestManager.httpRequest(POST, "users/login", jsonParams.toString());
        JSONObject jsonClient = new JSONObject(jsonResult[0].toString());

        if (jsonClient.has("_id")){
            String id = jsonClient.getString("_id");
            String email2 = jsonClient.getString("email");
            String role = jsonClient.getString("role");
            String token = jsonClient.getString("token");

            User.getInstance().setUser(id, email2, role, token);

            return true;
        }

        return false;
    }
}

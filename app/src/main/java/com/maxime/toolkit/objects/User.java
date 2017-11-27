package com.maxime.toolkit.objects;

import android.util.Log;

import com.maxime.toolkit.DataManager;

import java.util.ArrayList;

/**
 * Created by Maxime on 2017-10-22.
 */

public class User
{
    private static final User ourInstance = new User();

    private final String className = "app_User";
    private String id;
    private String email;
    private String role;
    private String token;

    public ArrayList<Category> categoryArray = new ArrayList<>();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }

    public Boolean isAdmin(){
        //return true;

        if (role == null)
            return false;

        if (role.equals("admin"))
            return true;
        else
            return false;
    }

    public Boolean isLivreur(){
        return true;
        /*
        if (role == null)
            return false;

        if (role.equals("livreur"))
            return true;
        else
            return false;*/
    }

    public Boolean isConnected(){
        if (email == null || email == "")
            return false;
        return true;
    }

    public void setUser(String _id, String _email, String _role, String _token){
        id = _id;
        email = _email;
        role = _role;
        token = _token;
    }

    public void resetUser(){
        id = null;
        email = null;
        role = null;
        token = null;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    /************************ Category stuff. Shouldn't be there but anyway ************************/

    public void setCategoryArray (ArrayList<Category> catList){
        this.categoryArray = catList;
    }

    public ArrayList<Category> getCategoryArray (){

        if (categoryArray.size() < 1){
            DataManager dataManager = new DataManager();
            dataManager.getAllCategories();
        }
        return this.categoryArray;
    }

    public Category[] getCategoryList (){
        Category[] categoryList = getCategoryArray().toArray(new Category[getCategoryArray().size()]);
        return categoryList;
    }

    public void selectCategory(int categoryID) {

        for(Category cat : getCategoryArray())
        {
            if(cat.getID() != 0 && cat.getID() == categoryID){
                cat.setSelected(true);
            }
            else
            {
                cat.setSelected(false);
            }
        }
    }

    public String getSelectedCategoryName(){

        String name = "All Products";
        for(Category cat : getCategoryArray())
        {
            if(cat.isSelected()){
                return cat.getName();
            }
        }

        return name;
    }

}

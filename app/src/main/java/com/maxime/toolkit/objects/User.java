package com.maxime.toolkit.objects;

import com.maxime.toolkit.DataManager;

import java.util.ArrayList;

/**
 * Created by Maxime on 2017-10-22.
 */

public class User
{
    private static final User ourInstance = new User();

    public ArrayList<Category> categoryArray = new ArrayList<>();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {

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

package com.cafe.bhaansa;

public class FoodMenuModel {
    String foodname, foodprice, foodimg;

    public FoodMenuModel() {
    }

    public FoodMenuModel(String foodname, String foodprice, String foodimg) {
        this.foodname = foodname;
        this.foodprice = foodprice;
        this.foodimg = foodimg;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodprice() {
        return foodprice;
    }

    public void setFoodprice(String foodprice) {
        this.foodprice = foodprice;
    }

    public String getFoodimg() {
        return foodimg;
    }

    public void setFoodimg(String foodimg) {
        this.foodimg = foodimg;
    }
}

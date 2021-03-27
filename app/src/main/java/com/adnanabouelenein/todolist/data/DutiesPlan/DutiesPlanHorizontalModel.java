package com.adnanabouelenein.todolist.data.DutiesPlan;

public class DutiesPlanHorizontalModel {
    private int categoryImage, numberOfTasks;
    private String categoryName;

    public DutiesPlanHorizontalModel(int categoryImage, int numberOfTasks, String categoryName) {
        this.categoryImage = categoryImage;
        this.numberOfTasks = numberOfTasks;
        this.categoryName = categoryName;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
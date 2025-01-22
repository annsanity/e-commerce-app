package com.ecommerce.projectapp.exception;

import com.ecommerce.projectapp.model.Category;

public class CategoryNotFoundException extends Exception {

    public CategoryNotFoundException(String categoryNotFound){
        super(categoryNotFound);
    }
}

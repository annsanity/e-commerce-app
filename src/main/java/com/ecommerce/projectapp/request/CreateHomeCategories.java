package com.ecommerce.projectapp.request;

import lombok.Data;

@Data
public class CreateHomeCategories {
    private String categoryId;
    private String name;
    private String image;
}
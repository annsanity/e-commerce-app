package com.ecommerce.projectapp.service;


import com.ecommerce.projectapp.model.Home;
import com.ecommerce.projectapp.model.HomeCategory;

import java.util.List;

public interface HomeCategoryService {

    HomeCategory createCategory(HomeCategory category);
    List<HomeCategory> createCategories(List<HomeCategory> categories);
    List<HomeCategory> getAllCategories();
    HomeCategory updateCategory(HomeCategory categories, Long id) throws Exception;

}

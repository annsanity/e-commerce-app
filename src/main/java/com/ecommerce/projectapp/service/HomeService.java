package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.model.Home;
import com.ecommerce.projectapp.model.HomeCategory;

import java.util.List;

public interface HomeService {

    Home createHomePageData(List<HomeCategory> categories);
}

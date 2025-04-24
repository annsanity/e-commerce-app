package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.model.Home;
import com.ecommerce.projectapp.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    @Override
    public Home createHomePageData() {
        // Returning static or placeholder data as categories and deals are removed
        Home home = new Home();
        home.setGrid(null);
        home.setShopByCategories(null);
        home.setElectricCategories(null);
        home.setDeals(null);
        home.setDealCategories(null);
        return home;
    }
}


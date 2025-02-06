package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.domain.HomeCategorySection;
import com.ecommerce.projectapp.model.Deal;
import com.ecommerce.projectapp.model.Home;
import com.ecommerce.projectapp.model.HomeCategory;
import com.ecommerce.projectapp.repository.DealRepository;
import com.ecommerce.projectapp.service.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final DealRepository dealRepository;

    @Override
    public Home createHomePageData(List<HomeCategory> categories) {

        List<HomeCategory> gridCategories = categories.stream().filter(category -> category.getSection() == HomeCategorySection.GRID).collect(Collectors.toList());

        List<HomeCategory> shopByCategories = categories.stream().filter(category -> category.getSection() == HomeCategorySection.SHOP_BY_CATEGORIES).collect(Collectors.toList());

        List<HomeCategory> electricCategories = categories.stream().filter(category -> category.getSection() == HomeCategorySection.ELECTRIC_CATEGORIES).collect(Collectors.toList());

        List<HomeCategory> dealCategories = categories.stream().filter(category -> category.getSection() == HomeCategorySection.DEALS).toList();

        List<Deal> createdDeals = new ArrayList<>();

        if (dealRepository.findAll().isEmpty()) {

            // A default discount of 10 % on all deal categories
            List<Deal> deals = categories.stream().filter(category -> category.getSection() == HomeCategorySection.DEALS).map(category -> new Deal(null, 10, category)).collect(Collectors.toList());
            createdDeals = dealRepository.saveAll(deals);
        } else {

            createdDeals = dealRepository.findAll();
        }

        Home home = new Home();
        home.setGrid(gridCategories);
        home.setShopByCategories(shopByCategories);
        home.setElectricCategories(electricCategories);
        home.setDeals(createdDeals);
        home.setDealCategories(dealCategories);

        return home;
    }
}

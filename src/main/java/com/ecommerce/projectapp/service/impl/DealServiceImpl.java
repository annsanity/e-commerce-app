package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.repository.DealRepository;
import com.ecommerce.projectapp.repository.HomeCategoryRepository;
import com.ecommerce.projectapp.service.DealService;

public class DealServiceImpl implements DealService {
    private final DealRepository dealRepository;
    private final HomeCategoryRepository homeCategoryRepository;

    @Autowired
    public DealServiceImpl(DealRepository dealRepository, HomeCategoryRepository homeCategoryRepository){
        this.dealRepository = dealRepository;
        this.homeCategoryRepository = homeCategoryRepository;
    }

    public Deal


}
package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.model.Deal;
import com.ecommerce.projectapp.model.HomeCategory;
import com.ecommerce.projectapp.repository.DealRepository;
import com.ecommerce.projectapp.repository.HomeCategoryRepository;
import com.ecommerce.projectapp.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealServiceImpl implements DealService {
    private final DealRepository dealRepository;
    private final HomeCategoryRepository homeCategoryRepository;

    @Autowired
    public DealServiceImpl(DealRepository dealRepository, HomeCategoryRepository homeCategoryRepository){
        this.dealRepository = dealRepository;
        this.homeCategoryRepository = homeCategoryRepository;
    }

    @Override
    public Deal createDeal(Deal deal) {
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);
        Deal newDeal = new Deal();
        newDeal.setCategory(category);
        newDeal.setDiscount(deal.getDiscount());
        return dealRepository.save(newDeal);
    }

    @Override
    public List<Deal> getDeals() {
        return dealRepository.findAll();
    }

    @Override
    public Deal updateDeal(Deal deal, Long id) throws Exception {
        Deal existingDeal = dealRepository.findById(id).orElse(null);
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);

        if(existingDeal != null){
            if(deal.getDiscount() != null){
                existingDeal.setDiscount(deal.getDiscount());
            }
            if(category != null){
                existingDeal.setCategory(category);
            }
            return dealRepository.save(existingDeal);
        }
        throw new Exception("Deal not found");
    }

    @Override
    public void deleteDeal(Long id) throws Exception {
        Deal deal = dealRepository.findById(id).orElse(null);

        if (deal != null) {
            dealRepository.delete(deal);
        }

    }

}
package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.model.HomeCategory;
import com.ecommerce.projectapp.repository.HomeCategoryRepository;
import com.ecommerce.projectapp.service.HomeCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeCategoryServiceImpl implements HomeCategoryService {

    private final HomeCategoryRepository homeCategoryRepository;

    public HomeCategoryServiceImpl(HomeCategoryRepository homeCategoryRepository){
        this.homeCategoryRepository = homeCategoryRepository;
    }

    @Override
    public HomeCategory createCategory(HomeCategory category) {

        return homeCategoryRepository.save(category);

    }

    public List<HomeCategory> createCategories(List<HomeCategory> categories) {
        if (homeCategoryRepository.findAll().isEmpty()) {
            return homeCategoryRepository.saveAll(categories);
        }
        return homeCategoryRepository.findAll();
    }

    @Override
    public List<HomeCategory> getAllCategories() {
        return homeCategoryRepository.findAll();
    }

    public HomeCategory updateCategory(HomeCategory category, Long id) throws Exception {
        HomeCategory existingCategory = homeCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category not found"));
        if(category.getImage()!=null){
            existingCategory.setImage(category.getImage());
        }
        if(category.getCategoryId()!=null){
            existingCategory.setCategoryId(category.getCategoryId());
        }
        return homeCategoryRepository.save(existingCategory);
    }


}

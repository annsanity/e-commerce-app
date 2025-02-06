package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.ProductException;
import com.ecommerce.projectapp.model.Category;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.Seller;
import com.ecommerce.projectapp.repository.CategoryRepository;
import com.ecommerce.projectapp.repository.ProductRepository;
import com.ecommerce.projectapp.request.CreateProductRequest;
import com.ecommerce.projectapp.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) throws ProductException {

        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());

        Category category1 = categoryRepository.findByCategory(req.getCategory());
        if(category1 == null){
            Category category = new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            category.setName(req.getCategory().replace("_"," "));
            category1=categoryRepository.save(category);
        }

        Category category2 = categoryRepository.findByCategory(req.getCategory2());
        if(category2==null){
            Category category = new Category();
            category.setCategoryId(req.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category.setName(req.getCategory2().replace("_"," "));
            category2 = categoryRepository.save(category);
        }
        Category category3 = categoryRepository.findByCategory(req.getCategory3());
        if(category3 == null){
            Category category = new Category();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category.setName(req.getCategory3().replace("_"," "));
            category3 = categoryRepository.save(category);
        }

        Product product=new Product();

        product.setSeller(seller);
        product.setCategory(category3);
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountPercent(discountPercentage);
        product.setSellingPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMrpPrice(req.getMrpPrice());
        product.setSizes(req.getSizes());
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    private int calculateDiscountPercentage(int totalPrice, int totalDiscountPrice) {

        if (totalPrice <= 0) {
            return 0;
        }

        double discount = totalPrice - totalDiscountPrice;
        double discountPercentage = (discount / totalPrice) * 100;

        return (int) discountPercentage;
    }

    @Override
    public void deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        productRepository.findById(productId);
        product.setId(productId);
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        return productRepository.findById(id).orElseThrow(()-> new ProductException("Product not Found"));
    }

    @Override
    public List<Product> searchProduct(String query) {
        return productRepository.searchProduct(query);
    }


    @Override
    public List<Product> recentlyAddedProduct() {
        return List.of();
    }

    @Override
    public List<Product> getProductBySellerId(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    // VERY IMPORTANT !!!!
    @Override
    public Page<Product> getAllProduct(String category, String brand, String color, String size,
                                       Integer minPrice, Integer maxPrice, Integer minDiscount,
                                       String sort, String stock, Integer pageNumber) {

        // Create specification
        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Category filter with JOIN
            if (category != null) {
                Join<Product, Category> categoryJoin = root.join("category");
                Predicate categoryPredicate = criteriaBuilder.or(
                        criteriaBuilder.equal(categoryJoin.get("categoryId"), category),
                        criteriaBuilder.equal(categoryJoin.get("parentCategory").get("categoryId"), category)
                );
                predicates.add(categoryPredicate);
            }

            // Simple equality filters
            if (color != null) predicates.add(criteriaBuilder.equal(root.get("color"), color));
            if (size != null) predicates.add(criteriaBuilder.equal(root.get("size"), size));

            // Range filters
            if (minPrice != null) predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice));
            if (maxPrice != null) predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));

            // Other filters
            if (minDiscount != null) predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercent"), minDiscount));
            if (stock != null) predicates.add(criteriaBuilder.equal(root.get("stock"), stock));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Create pageable
        Pageable pageable;
        if (sort != null && !sort.isEmpty()) {
            pageable = switch (sort) {
                case "price_low" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.by("sellingPrice").ascending());
                case "price_high" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.by("sellingPrice").descending());
                default -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.unsorted());
            };
        } else {
            pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.unsorted());
        }

        // Execute query with specification and pageable
        return productRepository.findAll(spec, pageable);
    }
}

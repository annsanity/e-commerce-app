package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.ProductException;
import com.ecommerce.projectapp.model.Category;
import com.ecommerce.projectapp.model.Product;
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
    public Product createProduct(CreateProductRequest req) throws ProductException {
        // Calculate discount percentage
        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());

        // Ensure categories exist or create them
        Category category1 = findOrCreateCategory(req.getCategory(), null, 1);
        Category category2 = findOrCreateCategory(req.getCategory2(), category1, 2);
        Category category3 = findOrCreateCategory(req.getCategory3(), category2, 3);

        // Create and save the product
        Product product = new Product();
        product.setCategory(category3);
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountPercent(discountPercentage);
        product.setSellingPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMrpPrice(req.getMrpPrice());
        product.setSizes(req.getSizes());
        product.setBrand(req.getBrand());
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    private Category findOrCreateCategory(String categoryId, Category parentCategory, int level) {
        Category category = categoryRepository.findByCategory(categoryId);
        if (category == null) {
            category = new Category();
            category.setCategoryId(categoryId);
            category.setLevel(level);
            category.setParentCategory(parentCategory);
            category.setName(categoryId.replace("_", " "));
            category = categoryRepository.save(category);
        }
        return category;
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
        // Ensure product exists
        findProductById(productId);
        product.setId(productId);
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with ID: " + id));
    }

    @Override
    public List<Product> searchProduct(String query) {
        return productRepository.searchProduct(query);
    }

    @Override
    public List<Product> recentlyAddedProduct() {
        return productRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @Override
    public List<Product> getAllProductsForAdmin() {
        return productRepository.findAll();
    }

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
            if (size != null) predicates.add(criteriaBuilder.like(root.get("sizes"), "%" + size + "%"));
            if (brand != null) predicates.add(criteriaBuilder.equal(root.get("brand"), brand));

            // Range filters
            if (minPrice != null) predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice));
            if (maxPrice != null) predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));

            // Other filters
            if (minDiscount != null) predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercent"), minDiscount));
            if (stock != null) {
                if (stock.equals("in_stock")) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("quantity"), 0));
                } else if (stock.equals("out_of_stock")) {
                    predicates.add(criteriaBuilder.equal(root.get("quantity"), 0));
                }
            }

            // Only active products
            predicates.add(criteriaBuilder.equal(root.get("active"), true));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Create pageable
        Pageable pageable;
        if (sort != null && !sort.isEmpty()) {
            pageable = switch (sort) {
                case "price_low" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.by("sellingPrice").ascending());
                case "price_high" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.by("sellingPrice").descending());
                case "newest" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.by("createdAt").descending());
                default -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.unsorted());
            };
        } else {
            pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.unsorted());
        }

        // Execute query with specification and pageable
        return productRepository.findAll(spec, pageable);
    }
}
package com.ecommerce.projectapp.mapper;

import com.ecommerce.projectapp.dto.ProductDto;
import com.ecommerce.projectapp.model.Product;

public class ProductMapper {

    public static ProductDto toProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(productDto.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setMrpPrice(product.getMrpPrice());
        productDto.setSellingPrice(product.getMrpPrice());
        productDto.setDiscountPercent(product.getDiscountPercent());
        productDto.setQuantity(product.getQuantity());
        productDto.setColor(product.getColor());
        productDto.setImages(product.getImages());
        productDto.setNumRatings(product.getNumRatings());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setSizes(product.getSizes());

        return productDto;
    }

    public Product mapToEntity(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setMrpPrice(productDto.getMrpPrice());
        product.setSellingPrice(productDto.getMrpPrice());
        product.setDiscountPercent(productDto.getDiscountPercent());
        product.setQuantity(productDto.getQuantity());
        product.setColor(productDto.getColor());
        product.setImages(productDto.getImages());
        product.setNumRatings(productDto.getNumRatings());
        product.setCreatedAt(productDto.getCreatedAt());
        product.setSizes(productDto.getSizes());
        return product;
    }
}

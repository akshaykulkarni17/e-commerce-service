package com.example.productService.util;

import com.example.productService.dto.ProductDTO;
import com.example.productService.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static Product toEntity(ProductDTO productDTO){
        Product product = new Product();
        BeanUtils.copyProperties(productDTO,product);
        return product;
    }

    public static ProductDTO toDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product,productDTO);
        return productDTO;
    }
}

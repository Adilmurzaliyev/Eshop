package com.adil.eshop.specifications;

import com.adil.eshop.domain.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> titleLike(String titleFilter) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + titleFilter + "%");
    }
}

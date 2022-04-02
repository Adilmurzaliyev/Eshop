package com.adil.eshop.services;

import com.adil.eshop.domain.Product;
import com.adil.eshop.repositories.ProductRepository;
import com.adil.eshop.specifications.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @Transactional
//    public List<Product> getAll() {
//    return productRepository.findAll();
//    }

    @Transactional
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void addOrUpdate(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public Page<Product> getByParams(Optional<String> nameFilter,
                                     Optional<BigDecimal> min,
                                     Optional<BigDecimal> max,
                                     Optional<Integer> page,
                                     Optional<Integer> size,
                                     Optional<String> sortField,
                                     Optional<String> sortOrder) {
//        if (!nameFilter.contains("%")) {
//            nameFilter = String.join("", "%", nameFilter, "%");
//        }
//        return productRepository.findProductByTitleLike(nameFilter);
        Specification<Product> specification = Specification.where(null);
        if (nameFilter.isPresent()) {
            specification = specification.and(ProductSpecification.titleLike(nameFilter.get()));
        }

        if (min.isPresent()) {
            specification = specification.and(ProductSpecification.greaterOrEquals(min.get()));
        }

        if (max.isPresent()) {
            specification = specification.and(ProductSpecification.lessOrEquals(max.get()));
        }

        if (sortField.isPresent() && sortOrder.isPresent()) {
            productRepository.findAll(specification,
                    PageRequest.of(page.orElse(1) -1, size.orElse(4),
                            Sort.by(Sort.Direction.fromString(sortOrder.get()), sortField.get())));
        }

        return productRepository.findAll(specification,
                PageRequest.of(page.orElse(1) -1, size.orElse(4)));
    }
}

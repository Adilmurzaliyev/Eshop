package com.adil.eshop.services;

import com.adil.eshop.domain.Product;
import com.adil.eshop.repositories.ProductRepository;
import com.adil.eshop.specifications.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Transactional
    public List<Product> getAll() {
    return productRepository.findAll();
    }

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
    public List<Product> getByTitle(String nameFilter) {
//        if (!nameFilter.contains("%")) {
//            nameFilter = String.join("", "%", nameFilter, "%");
//        }
//        return productRepository.findProductByTitleLike(nameFilter);
        Specification<Product> specification = Specification.where(null);
        specification = specification.and(ProductSpecification.titleLike(nameFilter));

        return productRepository.findAll(specification);
    }
}

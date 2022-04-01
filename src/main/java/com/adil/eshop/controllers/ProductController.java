package com.adil.eshop.controllers;

import com.adil.eshop.domain.Product;
import com.adil.eshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showAllProducts(Model model, @RequestParam(name = "titleFilter", required = false) String titleFilter) {
        if (titleFilter == null || titleFilter.isEmpty()) {
            model.addAttribute("products", productService.getAll());
        } else {
            model.addAttribute("products", productService.getByTitle(titleFilter));
        }
        return "product_views/products";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("products", productService.getById(id));
        return "product_views/product_form";
    }

    @PostMapping("/product_update")
    public String updateProduct(Product product) {
        productService.addOrUpdate(product);
        return "redirect:/products";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute(new Product());
        return "product_views/product_form";
    }

    @GetMapping("/delete/{id}")
    public String removeProduct(@PathVariable(value = "id") Long id) {
        productService.removeProduct(id);
        return "redirect:/products";
    }
}

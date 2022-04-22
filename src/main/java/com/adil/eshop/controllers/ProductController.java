package com.adil.eshop.controllers;

import com.adil.eshop.domain.Product;
import com.adil.eshop.services.ProductService;
import com.adil.eshop.services.exceptions.NotFoundExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showAllProducts(Model model,
                                  @RequestParam(name = "titleFilter", required = false) Optional<String> titleFilter,
                                  @RequestParam(name = "min", required = false) Optional<BigDecimal> min,
                                  @RequestParam(name = "max", required = false) Optional<BigDecimal> max,
                                  @RequestParam(name = "page", required = false) Optional<Integer> page,
                                  @RequestParam(name = "size", required = false) Optional<Integer> size,
                                  @RequestParam(name = "sortField", required = false) Optional<String> sortField,
                                  @RequestParam(name = "sortOrder", required = false) Optional<String> sortOrder){
        model.addAttribute("products", productService.getByParams(
                titleFilter, min, max, page, size, sortField, sortOrder));
//        if (titleFilter == null || titleFilter.isEmpty()) {
//            model.addAttribute("products", productService.getAll());
//        } else {
//            model.addAttribute("products", productService.getByParams(titleFilter));
//        }
        return "product_views/products";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("products", productService.getById(id).orElseThrow(NotFoundExceptions::new));
        return "product_views/product_form";
    }

    @PostMapping("/product_update")
    public String createOrUpdateProduct(Product product) {
        productService.addOrUpdate(product);
        return "redirect:/products";
    }

    @GetMapping("/new")
    public String newProduct(Model model, Product product) {
        model.addAttribute("products", product);
        return "product_views/product_form";
    }

    @GetMapping("/delete/{id}")
    public String removeProduct(@PathVariable(value = "id") Long id) {
        productService.removeProduct(id);
        return "redirect:/products";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionsHandler(NotFoundExceptions exceptions) {
        ModelAndView modelAndView = new ModelAndView("exception_views/not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}

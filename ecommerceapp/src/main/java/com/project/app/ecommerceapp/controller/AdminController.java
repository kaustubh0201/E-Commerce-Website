package com.project.app.ecommerceapp.controller;

import com.project.app.ecommerceapp.dto.ProductDTO;
import com.project.app.ecommerceapp.model.Category;
import com.project.app.ecommerceapp.model.Product;
import com.project.app.ecommerceapp.service.CategoryService;
import com.project.app.ecommerceapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("")
    public String adminHomePage() {
        return "adminHome";
    }

    // Category Section

    @GetMapping("/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/categories/add")
    public String addCategories(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String editAddCategories(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.deleteCategoryById(id);

        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {

        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }

        return "error";
    }

    // Product Section

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }

    @GetMapping("/products/add")
    public String addProducts(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/products/add")
    public String editAddProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName") String imgName) throws IOException {

        String imageUUID;
        if(!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDirectory, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = imgName;
        }

        Product product = new Product(
                productDTO.getId(),
                productDTO.getName(),
                categoryService.getCategoryById(productDTO.getCategoryId()).get(),
                productDTO.getPrice(),
                productDTO.getWeight(),
                productDTO.getDescription(),
                imageUUID
        );

        productService.addProduct(product);


        return "redirect:/admin/products";
    }

}

package com.project.app.ecommerceapp.repository;

import com.project.app.ecommerceapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findAllByCategory_Id(int id);

}

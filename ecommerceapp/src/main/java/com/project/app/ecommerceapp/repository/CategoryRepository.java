package com.project.app.ecommerceapp.repository;

import com.project.app.ecommerceapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}

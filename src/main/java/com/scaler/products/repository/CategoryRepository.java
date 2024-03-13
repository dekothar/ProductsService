package com.scaler.products.repository;

import com.scaler.products.dto.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByTitle(String categoryTitle);

    List<Category> findAll();
}

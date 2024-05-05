package com.scaler.products.repository;

import com.scaler.products.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryDto, Long> {

    CategoryDto findByTitle(String categoryTitle);

    List<CategoryDto> findAll();
}

package com.scaler.products.repository;

import com.scaler.products.dto.ProductDto;
import com.scaler.products.projection.productTitleAndId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductDto, Long> {

    ProductDto save(ProductDto product);

    ProductDto findByIdIs(Long id);

    Page<ProductDto> findAll(Pageable page);

    @Query("select p from ProductDto  p where p.category.title= :title and p.id = :id")
    ProductDto getParticularProductName(@Param("title") String title, @Param("id") Long id);

    @Query("select p.title as product_title,p.id as product_id from ProductDto p where p.category.id= :categoryId")
    List<productTitleAndId> getProductWithTitleAndId(@Param("categoryId") Long categoryId);

    List<ProductDto> findAllByCategory_Title(String title, Pageable page);

    List<ProductDto> findAllByCategory_TitleLike(String title);

    int countProductsByIdAndActive(Long id, int active);

    boolean existsProductByActive(int actiive);
}

package com.scaler.products;

import com.scaler.products.dto.CategoryDto;
import com.scaler.products.repository.CategoryRepository;
import com.scaler.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ProductsApplicationTests {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepository;
    @Test
    void contextLoads() {
        productRepo.findByIdIs(1L);
        System.out.println("deeppppppppppppppppppppppppppppppppppp");
        productRepo.findAll();
        // Hibernate Query execution
        /*List<productTitleAndId> productTitleAndId=productRepo.getProductWithTitleAndId(52L);
        System.out.println(productTitleAndId.get(0).getTitle());
        System.out.println(productTitleAndId.get(0).getId());*/

        Optional<CategoryDto> category=categoryRepository.findById(52L);
        CategoryDto category1=category.get();
        System.out.println("Fetching the Categories");

        // List<Product> productList=category.get().getProducts();

        System.out.println("Fetching Al the products for the categories");

    }

}

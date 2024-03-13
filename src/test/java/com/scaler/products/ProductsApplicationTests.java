package com.scaler.products;

import com.scaler.products.projection.productTitleAndId;
import com.scaler.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import java.util.List;

@SpringBootTest
class ProductsApplicationTests {

    @Autowired
    private ProductRepository productRepo;
    @Test
    void contextLoads() {
        productRepo.findByIdIs(1L);
        System.out.println("deeppppppppppppppppppppppppppppppppppp");
        productRepo.findAll();
        // Hibernate Query execution
        List<productTitleAndId> productTitleAndId=productRepo.getProductWithTitleAndId(52L);
        System.out.println(productTitleAndId.get(0).getTitle());
        System.out.println(productTitleAndId.get(0).getId());
    }

}

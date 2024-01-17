package com.shop;

import com.shop.dto.product.ProductDTO;
import com.shop.mappers.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShoppingMallApplicationTests {

    @Autowired
    private ProductMapper productMapper;


    @Test
    void contextLoads() {
//        ProductDTO aa = productMapper.get_product_by_no(500);
//
//        System.out.println(aa);
//
//        System.out.println(aa.getProductImages());
//        System.out.println("==========================================================================");
//        System.out.println(aa.getProductOptions());

//        List<ProductDTO> bb = productMapper.all_products(null, "price");
//        System.out.println(bb);

//        List<ProductDTO> aa = productMapper.aaaa();
//
//        System.out.println(aa);

//        List<ProductDTO> aaa = productMapper.get_big_category(11, null, "price");
//        System.out.println(aaa);

        System.out.println(productMapper.get_product_by_category(6));

    }

}

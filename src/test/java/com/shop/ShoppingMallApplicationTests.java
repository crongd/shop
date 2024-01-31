package com.shop;

import com.shop.dto.product.ProductDTO;
import com.shop.dto.shopping.OrderDTO;
import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import com.shop.mappers.OrderMapper;
import com.shop.mappers.ProductMapper;
import com.shop.mappers.ShoppingCartMapper;
import com.shop.mappers.UserMapper;
import com.shop.service.PortOneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ShoppingMallApplicationTests {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private PortOneService portOneService;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void contextLoads() {
//        ProductDTO aa = productMapper.get_product_by_no(500);


//        System.out.println(productMapper.get_product_by_category(7, "price"));
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

//        System.out.println(productMapper.get_product_by_category(6));

//        List<ProductDTO> aaaa = shoppingCartMapper.get_shopping_cart_by_user("jaeho9859");
//        System.out.println(aaaa);

//        List<ShoppingCartDTO> weq = shoppingCartMapper.get_shopping_cart_by_user(UserDTO.builder().id("jaeho9859").build());
//        System.out.println(weq);

//        String token = portOneService.get_access_token();
//        System.out.println(token);
//        String token = "5a06b390c1cdb55d9481777fb7bd9c07710d0138";

//        portOneService.pre_verification_order("2", "10");


//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        System.out.println(orderMapper.get_request_price(list));

//        List<Integer> cartNo = new ArrayList<>();
//        cartNo.add(3);
//        cartNo.add(4);
//
//        List<ShoppingCartDTO> list = shoppingCartMapper.select_cart_list_by_cartNo(cartNo);

//        List<OrderDTO> list = shoppingCartMapper.select_cart_list_by_userId(UserDTO.builder().id("jaeho9859").build());
        List<OrderDTO> list = orderMapper.get_orders();

        System.out.println(list);


    }

}

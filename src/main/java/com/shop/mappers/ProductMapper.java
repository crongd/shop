package com.shop.mappers;

import com.shop.dto.product.Category;
import com.shop.dto.product.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface ProductMapper {

    // no에 해당하는 상품 하나의 정보를 모두 가져와야ㅕ함
    ProductDTO get_product_by_no(int no);

    List<ProductDTO> all_products(@Param("category") int no, @Param("searchWord")String searchWord, @Param("order") String order);

    List<Category> get_categories(int cateNo);

    List<ProductDTO> get_product_by_category(@Param("cateNo")int cateNo,@Param("searchWord") String searchWord, @Param("order") String order);


    List<ProductDTO> get_big_category(@Param("no") int no, @Param("searchWord") String searchWord, @Param("order") String order);

    List<Category> get_category_of_product(Category category);

    /** */
//    // 카테고리 대분류 값 가져오기
//    List<Category> get_categories();
//    // 카테고리 대분류 이외 값 전부 가져오기
//    List<Category> get_category_by_no(int parentNo);


}

package com.shop.service;

import com.shop.dto.product.Category;
import com.shop.dto.product.ProductDTO;
import com.shop.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

    public ProductDTO get_product(int no) {
        return productMapper.get_product_by_no(no);
    }

    public List<ProductDTO> all_products(int no, String searchWord, String order){
        return productMapper.all_products(no, searchWord, order);
    }

    public List<ProductDTO> big_category_product(int no, String searchWord, String order) {
        return productMapper.get_big_category(no, searchWord, order);
    }

    public List<Category> get_categories(int cateNo) {
        return productMapper.get_categories(cateNo);
    }

    public List<ProductDTO> get_product_by_category(int cateNo, String searchWord, String order) {
        return productMapper.get_product_by_category(cateNo, searchWord, order);
    }

    public List<Category> get_category_of_product(Category category) {
        return productMapper.get_category_of_product(category);
    }

//    private String asdasf(String order) {
//        return switch () {
//            case "popular" -> "";
//            case "recent" -> "write_date";
//            case "popular" -> "";
//        }
//    }


    // 카테고리 조회
//    public List<Category> get_categories(){
//        return productMapper.get_categories();
//    }
//
//    public List<Category> get_categories(int parentNo){
//        return productMapper.get_category_by_no(parentNo);
//    }
}

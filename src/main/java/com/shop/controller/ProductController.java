package com.shop.controller;

import com.shop.dto.product.Category;
import com.shop.dto.product.ProductDTO;
import com.shop.dto.user.UserDTO;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{no}")
    public String get_product_main(@PathVariable("no") int no, Model model) {
//        System.out.println(no);
        ProductDTO productDTO = productService.get_product(no);
//        System.out.println(productDTO);
        if (Objects.isNull(productDTO)) {
            return "main/item-page-expire";
        }

        List<Category> categories = productService.get_category_of_product(productDTO.getCategory());
        model.addAttribute("product", productDTO);
//        System.out.println(categories);
        model.addAttribute("categories", categories);
        return "main/item-page";
    }

    @GetMapping
    @ResponseBody
    public List<ProductDTO> get_products(
            @RequestParam(name = "category") Integer no,
            @RequestParam(name = "searchWord", defaultValue = "") String searchWord,
            @RequestParam(name = "order", required = false, defaultValue = "") String order,
            Model model
    ) {
        System.out.println(no);
//        List<Category> categories = productService.get_categories();
//        model.addAttribute("category", productService.get_categories());

        if (no <= 4) {
//            System.out.println(productService.big_category_product(no, searchWord, order));
//            System.out.println("위");
            return productService.big_category_product(no, searchWord, order);
        } else {
//            System.out.println(productService.all_products(no, searchWord, order));
//            System.out.println("아래");
            return productService.all_products(no, searchWord, order);
        }
    }

    // 하위 카테고리 상품 조회
    @GetMapping("/category")
    @ResponseBody
    public List<ProductDTO> get_product_category_by_Child(@RequestParam(name = "cateNo") int cateNo,
                                                          @RequestParam(name = "searchWord", defaultValue = "") String searchWord,
                                                          @RequestParam(name = "order") String order) {
        System.out.println(cateNo);
        return productService.get_product_by_category(cateNo, searchWord, order);
    }


    // 대분류 카테고리 별 하위 카테고리 검색
    @GetMapping("/category/{cateNo}")
    @ResponseBody
    public List<Category> get_product_category(@PathVariable("cateNo") int cateNo) {
        return productService.get_categories(cateNo);
    }

    @PatchMapping("/review-like")
    @ResponseBody
    public void Patch_review_like(@AuthenticationPrincipal UserDTO userDTO,@RequestBody int reviewNo) {
        System.out.println(userDTO);
        System.out.println(reviewNo);
        productService.add_review_like(userDTO, reviewNo);
    }

}

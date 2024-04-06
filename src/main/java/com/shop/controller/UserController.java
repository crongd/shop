package com.shop.controller;

import com.shop.dto.product.ProductDTO;
import com.shop.dto.shopping.OrderDTO;
import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import com.shop.service.PortOneService;
import com.shop.service.UserMailService;
import com.shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final PortOneService portOneService;
    private final UserMailService userMailService;

    @GetMapping("/login")
    public void user_get_login() {}

    @GetMapping("/join")
    public void user_join() {}


    @GetMapping("/find")
    public void user_find() {}

    @GetMapping("/logout")
    public String user_logout(@AuthenticationPrincipal UserDTO userDTO, HttpSession session) {
        System.out.println(userDTO);
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/join")
    public String user_post_join(@Validated UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) { //Validated 에러 확인
            log.warn("userDTO 에러남");
            return "redirect:/user/join";
        }
        userService.join_user(userDTO);
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/cert/token")
    public String get_user_certification_token(){
        try{
            // 성공시에는 Token값 반환
            return portOneService.get_access_token();
        }catch (Exception e){
            // 실패시에는 빈문자열 반환
            log.error(e.getMessage());
            return "";
        }
    }

    @ResponseBody
    @PostMapping("/cert/{impUID}")
    public String get_user_certification_unique_key(
            @PathVariable String impUID,
            @RequestBody String token
    ){
        try{
            // 성공시에는 Token값 반환
            return portOneService.get_user_certification(impUID, token);
        }catch (Exception e){
            // 실패시에는 빈문자열 반환
            log.error(e.getMessage());
            return "";
        }
    }


    /* ***************************************************************************** */

    // id 찾기
    @ResponseBody
    @GetMapping("/find/id/{phoneNum}")
    public String find_user_id(@PathVariable("phoneNum") String phoneNum) {
       return userMailService.find_user_id(phoneNum);
    }

    // pw 재설정
    @ResponseBody
    @PostMapping("/find/pw")
    public String find_user_pw(@RequestBody UserDTO userDTO) {
        return userMailService.find_user_pw(userDTO);
    }

    @GetMapping("/password")
    public String re_password_page(@RequestParam("token") String token, Model model) {
        if (userMailService.find_user_by_token(token)) {
            model.addAttribute("token", token);
            return "user/password";
        }
        return "user/password-expire";
    }

    @PostMapping("/password_request")
    public String re_password_request(UserDTO userDTO) {
        log.warn(userDTO);
        userMailService.update_user_password(userDTO);
        return "redirect:login";
    }


    ///******************************************* 장바구니

    @GetMapping("/cart")
    public String get_shopping_cart_page(@AuthenticationPrincipal UserDTO userDTO, Model model) {
//        System.out.println(userDTO);
//        userService.get_shopping_cart_by_user();

        List<ShoppingCartDTO> productList = userService.get_shopping_cart_of_user(userDTO);
        System.out.println(productList.size());

        model.addAttribute("products", productList);
        return "main/bag";
    }




    @PostMapping("/cart")
    @ResponseBody
    public String add_shopping_cart(
            @AuthenticationPrincipal UserDTO userDTO,
            @RequestBody ShoppingCartDTO shoppingCartDTO
    ) {
        System.out.println(userDTO);
        System.out.println(shoppingCartDTO);


        shoppingCartDTO.setUser(userDTO);
        userService.add_product_in_shopping_cart(shoppingCartDTO);
        // 유저 장바구니 창으로 이동시킨다
        return "redirect:user/cart";
    }

    @PatchMapping("/cart")
    @ResponseBody
    public void change_product_amount_shopping_cart(@AuthenticationPrincipal UserDTO userDTO,
                                                    @RequestBody ShoppingCartDTO shoppingCartDTO) {
        System.out.println("/patch!@!");
        System.out.println(shoppingCartDTO);
        shoppingCartDTO.setUser(userDTO);
        userService.update_shopping_cart_product_amount(shoppingCartDTO);
//        return "redirect:/";
    }

    @DeleteMapping("/cart")
    @ResponseBody
    public void delete_product_of_shopping_cart(@AuthenticationPrincipal UserDTO userDTO,
                                                @RequestBody ShoppingCartDTO shoppingCartDTO) {
        System.out.println(shoppingCartDTO);
        shoppingCartDTO.setUser(userDTO);
        userService.delete_product_in_shopping_cart(shoppingCartDTO);
    }



//    @GetMapping("/cart/{productNo}/{productOption}")
//    public void insert_product_cart(@PathVariable int productNo, @PathVariable String productOption) {
//
//    }


    /**************************************** WISH LIST *************************/
    @GetMapping("wish-list")
    public String wish_list_view(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        System.out.println(userDTO);
        model.addAttribute("user", userDTO);
        model.addAttribute("wishList", userService.get_wish_list(userDTO));
        return "main/wish-list";
    }

    @PostMapping("/wish-list")
    @ResponseBody
    public String wish_list_add(
            @AuthenticationPrincipal UserDTO userDTO,
            @RequestBody int productNo
    ) {
        System.out.println(userDTO);
        System.out.println(productNo);
        userService.add_wish_list(userDTO, productNo);
        // 유저 찜목록 창으로 이동시킨다
        return "redirect:user/wish-list";
    }

    @DeleteMapping("/wish-list")
    @ResponseBody
    public void wish_list_delete(
            @AuthenticationPrincipal UserDTO userDTO,
            @RequestBody int productNo
    ) {
        System.out.println(userDTO);
        System.out.println(productNo);
        userService.delete_wish_list(userDTO, productNo);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/mypage")
    public String get_my_page_view(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        List<OrderDTO> orders = userService.get_my_page_data(userDTO);
        model.addAttribute("user", userDTO);
        model.addAttribute("orders", orders);
        return "user/my-page";
    }


    @GetMapping("/mypage/detail/{merchantUid}")
    @ResponseBody
    public List<ProductDTO> get_detail_data(
            @AuthenticationPrincipal UserDTO userDTO,
            @PathVariable("merchantUid") String merchantUid
    ) {
        System.out.println(userService.get_my_page_detail_data(userDTO, merchantUid));
        return userService.get_my_page_detail_data(userDTO, merchantUid);
    }







}

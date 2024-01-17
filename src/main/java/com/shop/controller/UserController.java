package com.shop.controller;

import com.shop.dto.shopping.ShoppingCartDTO;
import com.shop.dto.user.UserDTO;
import com.shop.service.UserCertificationService;
import com.shop.service.UserMailService;
import com.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final UserCertificationService userCertificationService;
    private final UserMailService userMailService;

    @GetMapping("/login")
    public void user_get_login() {

    }

    @GetMapping("/join")
    public void user_join() {}


    @GetMapping("/find")
    public void user_find() {}

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
            return userCertificationService.get_portone_access_token();
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
            return userCertificationService.get_user_certification(impUID, token);
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


    ///*******************************************

    @GetMapping("/cart")
    public String get_shopping_cart_page() {
        return "main/bag";
    }



    @PostMapping("/cart")
    public String add_shopping_cart(
            @AuthenticationPrincipal UserDTO userDTO,
                    ShoppingCartDTO shoppingCartDTO
    ){

        shoppingCartDTO.setUser(userDTO);
        userService.add_product_in_shopping_cart(shoppingCartDTO);
        // 유저 장바구니 창으로 이동시킨다
        return "redirect:/user/cart";
    }








}

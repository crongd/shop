package com.shop.service;

import com.shop.dto.user.UserDTO;
import com.shop.mappers.UserMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Log4j2
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserMapper userMapper;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.error("CustomOAuth2SuccessHandler");
        // 로그인 된 유저를 가져온다 (소셜 로그인 정보를 모두 가지고 있는 유저 객체 정보)
        UserDTO loginedUser = (UserDTO) authentication.getPrincipal();
        // 로그인 된 유저의 정보로 DB에서 탐색한다 (해당 소셜의 정보만)
        // 찾은 결과
//        UserDTO findedUserDTO = userMapper.find_user(loginedUser.getId(), true);


        // 그 소셜 회원가입(등록) 기록이 있는가?
        // 기록이 없었다. => 소셜 회원가입(등록) 해야한다.
        if (loginedUser.getId().equals("temporary-custom-sns-user")) {

            // 회원가입을 처음부터 끝까지 진행시킨다.
            request.getSession().invalidate();

            HttpSession session = request.getSession(true);
            session.setAttribute("snsUser", loginedUser);
//            request.getRequestDispatcher("/user/join").forward(request, response);
            response.sendRedirect("/user/join");
            return;

        }
        response.sendRedirect("/");
    }

}

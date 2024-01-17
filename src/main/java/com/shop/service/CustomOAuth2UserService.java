package com.shop.service;

import com.shop.dto.ImageFileDTO;
import com.shop.dto.user.SnsInfoDTO;
import com.shop.dto.user.UserDTO;
import com.shop.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

//    배포할 땐 지워야함
//    private final String USER_CI = "5NISZeDzJAR/trCb4GNlF74xvJsAxYZ0JO7HCOt35a2VqxjL75d+LSrGnXvYV1rOOEk7rP+c6u+I6cmJitmvmA==";
    private final String USER_CI = "+c6u+I6cmJitmvmA==";


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.warn("=========== CustomOAuth2UserService ==============");
        log.warn(userRequest);
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName().toUpperCase();
        log.warn("[" + clientName + "]" + "(으)로 로그인 중입니다....");

        Map<String, Object> userPropertiesMap = create_user_properties_map();

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        switch (clientName) {
            case "KAKAO" -> get_kakao_properties(paramMap, userPropertiesMap, clientName);
            case "GOOGLE" -> get_google_properties(paramMap, userPropertiesMap, clientName);
            case "NAVER" -> get_naver_properties(paramMap, userPropertiesMap, clientName);
        }

        // 로그인 된 유저를 가져온다 ( 소셜 로그인 정보를 모두 가지고 있는 유저 객체 정보 )
        // 여기서 로그인 된 유저는 철저히 SNS유저 상태의 정보임.
        UserDTO loginedUser = create_userDTO(userPropertiesMap, userRequest.getAccessToken().getTokenValue());
        // 로그인 된 유저의 정보로 DB에서 탐색한다 ( 해당 소셜의 정보만 )
        loginedUser.getSnsInfo().setClientName(clientName);
        UserDTO findedUserDTO = userMapper.find_user(loginedUser, true);

        if (Objects.isNull(findedUserDTO)) {
            return loginedUser;
        }


        // 그 소셜 회원가입(등록) 기록이 있는가?
        // 기록이 없었다. => 소셜 회원가입(등록) 해야 한다.
        if((Objects.isNull(findedUserDTO.getSnsInfo()))) {
            findedUserDTO.setSnsInfo(loginedUser.getSnsInfo());
            // 근데 유저 자체도 없네 ?? => 유저 회원가입(첫 등록)도 해야 한다.
            // 새로운 소셜 로그인만 등록하면 된다. 유저 정보에 새로운 소셜 정보를 추가한다...
            userMapper.insert_sns_info(findedUserDTO);
            // 찾은 기본 유저 정보 + 현재 로그인한 SNS 정보 합체
        }
        // 현재 로그인 한 유저를 DB 유저로 교체한다
        return findedUserDTO;

    }

    public UserDTO create_userDTO(Map<String, Object> userPropertiesMap, String token) {
        String snsInfoId = (String) userPropertiesMap.get("id");
        String snsInfoClientName = (String) userPropertiesMap.get("clientName");
        String snsInfoProfileImage= (String) userPropertiesMap.get("profile_image");
        String snsInfoMobile = (String) userPropertiesMap.get("mobile");
        String snsInfoEmail = (String) userPropertiesMap.get("email");

        SnsInfoDTO snsInfoDTO = SnsInfoDTO.builder()
                .id(snsInfoId)
                .clientName(snsInfoClientName)
                .connectDate(LocalDateTime.now())
                .attributes(userPropertiesMap)
                .build();

        ImageFileDTO imageFileDTO = ImageFileDTO.builder()
                .originalFileName(snsInfoProfileImage)
                .savedFileName(snsInfoProfileImage)
                .build();

        return UserDTO.builder()
                .id("temporary-custom-sns-user")
                .ci(USER_CI)
                .accessToken(token)
                .tel(snsInfoMobile)
                .imageFile(imageFileDTO)
                .email(snsInfoEmail)
                .snsInfo(snsInfoDTO)
                .build();
    }

    public Map<String, Object> create_user_properties_map() {
        Map<String, Object> userPropertiesMap = new HashMap<>();
        userPropertiesMap.put("id", null);
        userPropertiesMap.put("Name", null);
        userPropertiesMap.put("email", null);
        userPropertiesMap.put("mobile", null);
        userPropertiesMap.put("profile_image", null);

        return userPropertiesMap;
    }

    public void get_kakao_properties(Map<String, Object> paramMap, Map<String, Object> userPropertiesMap, String clientName) {
        Map<String, Object> propertyMap = (Map<String, Object>) paramMap.get("properties");
        Long id = (Long) paramMap.get("id");
        String name = (String) propertyMap.get("nickname"); //닉네임
        String profileImage = (String) propertyMap.get("profile_image"); //프로필 사진

        userPropertiesMap.put("id", id.toString());
        userPropertiesMap.put("name", name);
        userPropertiesMap.put("profileImage", profileImage);
        userPropertiesMap.put("clientName", clientName);

    }

    public void get_google_properties(Map<String, Object> paramMap, Map<String, Object> userPropertiesMap, String clientName) {
        String id = (String) paramMap.get("sub"); // id
        String name = (String) paramMap.get("name"); // 이름
        String email = (String) paramMap.get("email"); // 이메일
        String profileImage = (String) paramMap.get("picture"); // 프로필 사진

        userPropertiesMap.put("id", id);
        userPropertiesMap.put("name", name);
        userPropertiesMap.put("email", email);
        userPropertiesMap.put("profileImage", profileImage);
        userPropertiesMap.put("clientName", clientName);

    }

    public void get_naver_properties(Map<String, Object> paramMap, Map<String, Object> userPropertiesMap, String clientName) {
        Map<String, Object> propertyMap = (Map<String, Object>) paramMap.get("response");
        String id = (String) propertyMap.get("id");
        String email = (String) propertyMap.get("email");
        String mobile = (String) propertyMap.get("mobile");
        String profileImage = (String) propertyMap.get("profile_image");

        userPropertiesMap.put("id", id);
        userPropertiesMap.put("email", email);
        userPropertiesMap.put("mobile", mobile);
        userPropertiesMap.put("profileImage", profileImage);
        userPropertiesMap.put("clientName", clientName);

    }

}

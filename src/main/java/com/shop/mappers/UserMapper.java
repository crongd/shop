package com.shop.mappers;

import com.shop.dto.user.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface UserMapper {

    // 유저 회원가입(모든정보(SNS정보 제외))
    void join_user(UserDTO userDTO);

    UserDTO find_user(@Param("userDTO") UserDTO userDTO,@Param("isSNS") boolean isSNS);

    // 기존 회원가입 유저에 sns 정보 등록하기
    void insert_sns_info(UserDTO userDTO);

    // 아이디 찾기
    String fine_user_id(String phoneNum);

    @Select("select * from `user` where `id`= #{id} and `email`= #{email}")
    UserDTO find_user_by_email(UserDTO userDTO);

    // 유저가 패스워드 재설정 이메일의 링크를 클릭했다면 해당 토큰 값을 가진 유저를 가져오기
    @Select("select * from `user` where `pw_re_token` = #{token}")
    UserDTO find_user_by_token(String token);


    @Update("update `user` set `pw_re_token`=#{pwReToken}, `pw_re_token_expire`=#{pwReTokenExpire} where `id`=#{id}")
    void update_user_repw_token(UserDTO userDTO);

    @Update("update `user` set `password` = #{password} where `pw_re_token`=#{pwReToken}")
    void update_user_password(UserDTO userDTO);

    void insert_wishlist(
            @Param("userId") String userId,
            @Param("productNo") int productNo
    );

}

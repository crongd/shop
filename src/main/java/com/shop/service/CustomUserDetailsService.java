package com.shop.service;

import com.shop.dto.user.SnsInfoDTO;
import com.shop.dto.user.UserDTO;
import com.shop.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("loadUserByUsername run");
        SnsInfoDTO snsInfoDTO = SnsInfoDTO.builder().build();
        UserDTO setUserDTO = UserDTO.builder().id(username).snsInfo(snsInfoDTO).build();

        UserDTO userDTO = userMapper.find_user(setUserDTO, false);

        if (Objects.isNull(userDTO)) {
            throw new UsernameNotFoundException("username이 존재하지 않음, [" + username + "]");
        }

        return userDTO;
    }
}

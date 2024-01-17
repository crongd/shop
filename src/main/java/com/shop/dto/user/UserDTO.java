package com.shop.dto.user;


import com.shop.dto.ImageFileDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements OAuth2User, UserDetails {
    @NotBlank(message = "민 5, 맥 16")
    @Length(min = 6, max = 12)
    private String id;
    @NotBlank @Length(min = 88)
    private String ci;
    @NotBlank
    @Length(min = 8, max = 16)
    private String password;
    @Email
    private String email;
    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}")
    private String tel;
    private ImageFileDTO imageFile;
    private Collection<? extends GrantedAuthority> authorities;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;
    private String accessToken;
    private String pwReToken;
    private LocalDateTime pwReTokenExpire;
    private SnsInfoDTO snsInfo;



    @Override
    public Map<String, Object> getAttributes() {
        return this.snsInfo.getAttributes();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        this.authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return this.id;
    }
}

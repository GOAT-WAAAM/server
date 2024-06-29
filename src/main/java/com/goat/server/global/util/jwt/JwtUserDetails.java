package com.goat.server.global.util.jwt;

import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.domain.type.Role;
import lombok.Builder;

@Builder
public record JwtUserDetails(
        Long userId,
        Role role
) {
    public static JwtUserDetails from(User user) {
        return new JwtUserDetails(user.getUserId(), user.getRole());
    }
}

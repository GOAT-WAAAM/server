package com.goat.server.global.domain;

import com.goat.server.mypage.domain.type.Role;
import lombok.Builder;

@Builder
public record JwtUserDetails(
        Long userId,
        Role role
) {
    public static JwtUserDetails of(Long userId, Role role) {
        return new JwtUserDetails(userId, role);
    }
}

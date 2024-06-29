package com.goat.server.mypage.dto;

public record JwtUserDetailProjection(
        Long userId,
        String role
){
}

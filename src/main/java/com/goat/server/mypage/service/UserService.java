package com.goat.server.mypage.service;

import com.goat.server.auth.dto.response.KakaoUserResponse;
import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.domain.type.Role;
import com.goat.server.mypage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저 회원가입
     */
    public User createUser(final KakaoUserResponse userResponse) {
        User user = User.builder()
                .socialId(userResponse.id().toString())
                .nickname(userResponse.kakaoAccount().profile().nickname())
                .role(Role.GUEST)
                .build();

        return userRepository.save(user);
    }

}

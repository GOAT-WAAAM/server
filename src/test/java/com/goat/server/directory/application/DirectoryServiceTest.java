package com.goat.server.directory.application;


import static com.goat.server.directory.fixture.DirectoryFixture.PARENT_DIRECTORY1;
import static com.goat.server.directory.fixture.DirectoryFixture.PARENT_DIRECTORY2;
import static com.goat.server.mypage.fixture.UserFixture.USER_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.goat.server.directory.dto.response.DirectoryResponse;
import com.goat.server.directory.dto.response.DirectoryResponseList;
import com.goat.server.directory.repository.DirectoryRepository;
import com.goat.server.mypage.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DirectoryServiceTest {

    @InjectMocks
    private DirectoryService directoryService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DirectoryRepository directoryRepository;

    @Test
    @DisplayName("과목과 폴더 가져 오기 테스트")
    void getDirectoryListTest() {
        //given
        given(userRepository.findById(USER_USER.getUserId())).willReturn(Optional.of(USER_USER));
        given(directoryRepository.findAllByUserAndParentDirectoryIsNull(USER_USER))
                .willReturn(List.of(PARENT_DIRECTORY1, PARENT_DIRECTORY2));

        //when
        DirectoryResponseList directoryList = directoryService.getDirectoryList(USER_USER.getUserId());

        //then
        assertThat(directoryList.directoryResponseList())
                .extracting(DirectoryResponse::directoryId)
                .containsExactly(PARENT_DIRECTORY1.getDirectoryId(), PARENT_DIRECTORY2.getDirectoryId());
    }
}
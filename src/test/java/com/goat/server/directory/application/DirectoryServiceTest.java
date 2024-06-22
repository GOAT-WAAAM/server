package com.goat.server.directory.application;

import static com.goat.server.directory.fixture.DirectoryFixture.CHILD_DIRECTORY1;
import static com.goat.server.directory.fixture.DirectoryFixture.CHILD_DIRECTORY2;
import static com.goat.server.directory.fixture.DirectoryFixture.TRASH_DIRECTORY;
import static com.goat.server.directory.fixture.DirectoryFixture.PARENT_DIRECTORY1;
import static com.goat.server.directory.fixture.DirectoryFixture.PARENT_DIRECTORY2;
import static com.goat.server.mypage.fixture.UserFixture.USER_USER;
import static com.goat.server.review.fixture.ReviewFixture.DUMMY_REVIEW1;
import static com.goat.server.review.fixture.ReviewFixture.DUMMY_REVIEW2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.goat.server.directory.domain.Directory;
import com.goat.server.directory.dto.request.DirectoryInitRequest;
import com.goat.server.directory.dto.request.DirectoryMoveRequest;
import com.goat.server.directory.dto.response.DirectoryResponse;
import com.goat.server.directory.dto.response.DirectoryTotalShowResponse;
import com.goat.server.directory.repository.DirectoryRepository;
import com.goat.server.mypage.application.UserService;
import com.goat.server.review.application.ReviewService;
import com.goat.server.review.dto.response.ReviewSimpleResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DirectoryServiceTest {

    @InjectMocks
    private DirectoryService directoryService;

    @Mock
    private DirectoryRepository directoryRepository;

    @Mock
    private ReviewService reviewService;

    @Mock
    private UserService userService;

    @Captor
    ArgumentCaptor<Directory> directoryCaptor;

    @Test
    @DisplayName("과목과 폴더 가져 오기 테스트 - 루트 폴더 X")
    void getDirectoryListTest() {
        //given
        given(directoryRepository.existsById(PARENT_DIRECTORY1.getId()))
                .willReturn(true);
        given(directoryRepository.findAllByParentDirectoryId(eq(PARENT_DIRECTORY1.getId()), any()))
                .willReturn(List.of(CHILD_DIRECTORY1, CHILD_DIRECTORY2));
        given(reviewService.getReviewSimpleResponseList(eq(PARENT_DIRECTORY1.getId()), any())).willReturn(
                List.of(ReviewSimpleResponse.from(DUMMY_REVIEW1), ReviewSimpleResponse.from(DUMMY_REVIEW2)));

        //when
        DirectoryTotalShowResponse directorySubList =
                directoryService.getDirectorySubList(USER_USER.getUserId(), PARENT_DIRECTORY1.getId(), null);

        //then
        assertThat(directorySubList.directoryResponseList())
                .extracting(DirectoryResponse::directoryId)
                .containsExactly(CHILD_DIRECTORY1.getId(), CHILD_DIRECTORY2.getId());

        assertThat(directorySubList.reviewSimpleResponseList())
                .extracting(ReviewSimpleResponse::reviewId)
                .containsExactly(DUMMY_REVIEW1.getId(), DUMMY_REVIEW2.getId());
    }

    @Test
    @DisplayName("과목과 폴더 가져 오기 테스트 - 루트 폴더")
    void getDirectoryListTest2() {
        //given
        given(directoryRepository.findAllByUserUserIdAndParentDirectoryIsNull(USER_USER.getUserId()))
                .willReturn(List.of(PARENT_DIRECTORY1, PARENT_DIRECTORY2));

        //when
        DirectoryTotalShowResponse directorySubList =
                directoryService.getDirectorySubList(USER_USER.getUserId(), 0L, null);

        //then
        assertThat(directorySubList.directoryResponseList())
                .extracting(DirectoryResponse::directoryId)
                .containsExactly(PARENT_DIRECTORY1.getId(), PARENT_DIRECTORY2.getId());

        assertThat(directorySubList.reviewSimpleResponseList()).isEmpty();
    }

    @Test
    @DisplayName("폴더 임시 삭제 테스트")
    void deleteDirectoryTemporalTest() {
        //given
        given(directoryRepository.findById(PARENT_DIRECTORY1.getId()))
                .willReturn(Optional.of(PARENT_DIRECTORY1));
        given(directoryRepository.findTrashDirectoryByUser(USER_USER.getUserId()))
                .willReturn(Optional.of(TRASH_DIRECTORY));

        //when
        directoryService.deleteDirectoryTemporal(USER_USER.getUserId(), PARENT_DIRECTORY1.getId());

        //then
        assertThat(PARENT_DIRECTORY1.getParentDirectory()).isEqualTo(TRASH_DIRECTORY);
    }

    @Test
    @DisplayName("폴더 생성 테스트 - 루트 폴더")
    void initDirectoryTest() {
        //given
        DirectoryInitRequest directoryInitRequest =
                new DirectoryInitRequest("폴더", 0L, "#FF0000");

        given(userService.findUser(USER_USER.getUserId())).willReturn(USER_USER);

        //when
        directoryService.initDirectory(USER_USER.getUserId(), directoryInitRequest);

        //then
        verify(directoryRepository, times(1)).save(directoryCaptor.capture());

        Directory savedDirectory = directoryCaptor.getValue();
        assertThat(savedDirectory.getTitle()).isEqualTo(directoryInitRequest.directoryName());
        assertThat(savedDirectory.getParentDirectory()).isNull();
        assertThat(savedDirectory.getDirectoryColor()).isEqualTo(directoryInitRequest.directoryColor());
    }

    @Test
    @DisplayName("폴더 생성 테스트 - 루트 폴더 X")
    void initDirectoryTest2() {
        //given
        DirectoryInitRequest directoryInitRequest =
                new DirectoryInitRequest("폴더", PARENT_DIRECTORY1.getId(), "#FF0000");

        given(userService.findUser(USER_USER.getUserId())).willReturn(USER_USER);
        given(directoryRepository.findById(PARENT_DIRECTORY1.getId()))
                .willReturn(Optional.of(PARENT_DIRECTORY1));

        //when
        directoryService.initDirectory(USER_USER.getUserId(), directoryInitRequest);

        //then
        verify(directoryRepository, times(1)).save(directoryCaptor.capture());

        Directory savedDirectory = directoryCaptor.getValue();
        assertThat(savedDirectory.getTitle()).isEqualTo(directoryInitRequest.directoryName());
        assertThat(savedDirectory.getParentDirectory()).isEqualTo(PARENT_DIRECTORY1);
        assertThat(savedDirectory.getDirectoryColor()).isEqualTo(directoryInitRequest.directoryColor());
    }

    @Test
    @DisplayName("폴더 이동 테스트")
    void moveDirectoryTest() {
        //given
        DirectoryMoveRequest request = new DirectoryMoveRequest(PARENT_DIRECTORY1.getId(), PARENT_DIRECTORY2.getId());

        given(directoryRepository.findById(PARENT_DIRECTORY1.getId()))
                .willReturn(Optional.of(PARENT_DIRECTORY1));
        given(directoryRepository.findById(PARENT_DIRECTORY2.getId()))
                .willReturn(Optional.of(PARENT_DIRECTORY2));

        //when
        directoryService.moveDirectory(request);

        //then
        assertThat(PARENT_DIRECTORY1.getParentDirectory()).isEqualTo(PARENT_DIRECTORY2);
        assertThat(PARENT_DIRECTORY1.getDepth()).isEqualTo(PARENT_DIRECTORY2.getDepth() + 1);
    }
}
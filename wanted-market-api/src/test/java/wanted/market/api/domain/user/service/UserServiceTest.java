package wanted.market.api.domain.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.market.api.domain.user.entity.User;
import wanted.market.api.domain.user.repository.UserRepository;
import wanted.market.api.global.response.exception.WantedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;



    @Test
    @DisplayName("Success - is User")
    void isUser(){
        Long userId=1L;
        // given
        User user = User.builder().nickname("testNickname").build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // then
        assertEquals("testNickname", userService.getUser(userId).getNickname());
    }
    @Test
    @DisplayName("Failure - is not User")
    void isNotUser(){
        Long userId=1L;
        // when
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // then
        assertThrows(WantedException.class, ()->userService.getUser(userId));
    }
}
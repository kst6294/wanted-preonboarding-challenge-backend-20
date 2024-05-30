package wanted.market.api.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
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

    @Mock
    private HttpServletRequest request;

    @Test
    @DisplayName("Success - is User")
    void isUser(){
        // given
        User user = User.builder().nickname("testNickname").build();
        Long userId = 1L;
        when(request.getHeader("userId")).thenReturn("1");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // then
        assertEquals("testNickname", userService.getUser(request).getNickname());
    }
    @Test
    @DisplayName("Failure - is not User")
    void isNotUser(){
        Long userId=1L;
        // when
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // then
        assertThrows(WantedException.class, ()->userService.getUser(request));
    }
}
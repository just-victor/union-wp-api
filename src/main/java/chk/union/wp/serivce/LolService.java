package chk.union.wp.serivce;

import chk.union.wp.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LolService {
    private final AuthService authService;
    private final UserService userService;

    public Map<String, UserDto> getRegisteredUsers() {
        return authService.getAll();
    }

    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

}

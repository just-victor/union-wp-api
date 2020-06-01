package chk.union.wp.controller;

import chk.union.wp.common.response.ResponseWrapper;
import chk.union.wp.dto.UserDto;
import chk.union.wp.entity.User;
import chk.union.wp.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static chk.union.wp.security.SecurityFilter.HEADER_X_SECURITY_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseWrapper<User> register(@RequestBody UserDto userDto, @RequestHeader(AUTHORIZATION) String authorization) {
        User registeredUser = userService.register(userDto, authorization);

        return ResponseWrapper.of(registeredUser);
    }

    @GetMapping("/getUserData")
    public ResponseWrapper<UserDto> getUserData(@RequestHeader(HEADER_X_SECURITY_TOKEN) String token) {
        return ResponseWrapper.of(userService.getUserData(token));
    }

    @GetMapping("/search")
    public ResponseWrapper<List<UserDto>> search(@RequestParam("name") String name) {
        return ResponseWrapper.of(userService.search(name));
    }
}

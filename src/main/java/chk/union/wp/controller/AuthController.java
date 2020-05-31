package chk.union.wp.controller;

import chk.union.wp.common.response.ResponseWrapper;
import chk.union.wp.dto.LoginDto;
import chk.union.wp.serivce.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseWrapper<String> login(@RequestBody final LoginDto logindto) {
        return ResponseWrapper.of(loginService.login(logindto));
    }
}

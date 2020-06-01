package chk.union.wp.controller;

import chk.union.wp.dto.UserDto;
import chk.union.wp.serivce.AuthService;
import chk.union.wp.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/lol")
@RequiredArgsConstructor
public class LolController {
    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/registeredUsers")
    public Map<String, UserDto> getRegisteredUses() {
        return authService.getAll();
    }

    @GetMapping("/allUsers")
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("")
    public List<String> get() {
        return Arrays.stream(LolController.class.getMethods())
                .map(method -> {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        return "GET: " + method.getAnnotation(GetMapping.class).value()[0];
                    } else
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        return "POST: " + method.getAnnotation(PostMapping.class).value()[0];
                    } else
                    if (method.isAnnotationPresent(PutMapping.class)) {
                        return "PUT: " + method.getAnnotation(PutMapping.class).value()[0];
                    } else return null;

                }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}

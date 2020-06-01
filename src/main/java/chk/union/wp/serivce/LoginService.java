package chk.union.wp.serivce;

import chk.union.wp.common.exception.NotFoundException;
import chk.union.wp.dto.LoginDto;
import chk.union.wp.entity.Session;
import chk.union.wp.entity.User;
import chk.union.wp.repostiory.SessionRepository;
import chk.union.wp.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;
    private final SessionRepository sessionRepository;
    private final AuthService authService;

    public String login(final LoginDto loginDto) {
        LOG.info("User \"{}\" try to login", loginDto.getTelephone());

        User registeredUser = userService.findByTelephone(loginDto.getTelephone())
                .orElseThrow(NotFoundException::new);

        String password = new String(Base64.getDecoder().decode(loginDto.getPassword()));

        PasswordUtils.verifyUserPassword(registeredUser, password);

        Session session = new Session();

        session.setUser(registeredUser);
        session.setSecurityToken(UUID.randomUUID().toString());

        sessionRepository.save(session);

        LOG.info("User \"{}\" successfully logged in", loginDto.getTelephone());

        return session.getSecurityToken();
    }

    public void logout() {
        authService.logoutCurrentUser();
    }
}

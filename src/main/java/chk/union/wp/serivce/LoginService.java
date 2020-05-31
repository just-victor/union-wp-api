package chk.union.wp.serivce;

import chk.union.wp.common.exception.NotFoundException;
import chk.union.wp.common.exception.UnauthorizedException;
import chk.union.wp.dto.LoginDto;
import chk.union.wp.entity.Session;
import chk.union.wp.entity.User;
import chk.union.wp.repostiory.SessionRepository;
import chk.union.wp.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;
    private final SessionRepository sessionRepository;

    public String login(final LoginDto loginDto) {
        User registeredUser = userService.findByTelephone(loginDto.getTelephone())
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        String password = new String(Base64.getDecoder().decode(loginDto.getPassword()));

        PasswordUtils.verifyUserPassword(registeredUser, password);

        Session session = new Session();

        session.setUser(registeredUser);
        session.setSecurityToken(UUID.randomUUID().toString());

        sessionRepository.save(session);

        return session.getSecurityToken();
    }
}

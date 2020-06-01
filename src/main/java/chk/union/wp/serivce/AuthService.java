package chk.union.wp.serivce;

import chk.union.wp.common.exception.UnauthorizedException;
import chk.union.wp.dto.UserDto;
import chk.union.wp.entity.Session;
import chk.union.wp.mapper.UserMapper;
import chk.union.wp.repostiory.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static chk.union.wp.security.SecurityFilter.HEADER_X_SECURITY_TOKEN;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;
    private final HttpServletRequest request;

    private Map<String, UserDto> authorizedUsers = new HashMap<>();

    public Optional<UserDto> getAuthorizedUser (final String securityToken) {
        LOG.info("Retrieving user by security token {}", securityToken);
        UserDto userDto = authorizedUsers.get(securityToken);
        if (isNull(userDto)) {
            Session session = sessionRepository.findFirstBySecurityToken(securityToken)
                    .orElseThrow(UnauthorizedException::new);
            userDto = userMapper.toDto(session.getUser());
            authorizedUsers.put(securityToken, userDto);
        }

        return Optional.of(userDto);
    }

    public UserDto getCurrentUser() {
        String securityToken = request.getHeader(HEADER_X_SECURITY_TOKEN);

        return getAuthorizedUser(securityToken)
                .orElseThrow(UnauthorizedException::new);
    }

    public Map<String, UserDto> getAll() {
        return authorizedUsers;
    }

    public void logoutCurrentUser() {
        String securityToken = request.getHeader(HEADER_X_SECURITY_TOKEN);
        LOG.info("Log out current user token: {}, name: {}", securityToken, getAuthorizedUser(securityToken)
                .orElse(new UserDto()).getName());
        authorizedUsers.remove(securityToken);
        sessionRepository.deleteBySecurityToken(securityToken);
    }
}

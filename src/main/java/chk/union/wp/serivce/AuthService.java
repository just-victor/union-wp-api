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

    public UserDto getAuthorizedUser (final String securityToken) {
        UserDto userDto = authorizedUsers.get(securityToken);
        if (isNull(userDto)) {
            Session session = sessionRepository.findFirstBySecurityToken(securityToken)
                    .orElseThrow(() -> new UnauthorizedException("User is not authorized: " + securityToken));
            userDto = userMapper.toDto(session.getUser());
            authorizedUsers.put(securityToken, userDto);
        }

        return userDto;
    }

    public UserDto getCurrentUserDto() {
        String securityToken = request.getHeader(HEADER_X_SECURITY_TOKEN);

        return getAuthorizedUser(securityToken);
    }

    public Map<String, UserDto> getAll() {
        return authorizedUsers;
    }

    public void logoutCurrentUser() {
        String securityToken = request.getHeader(HEADER_X_SECURITY_TOKEN);
        LOG.info("Log out current user token: {}, name: {}", securityToken, getAuthorizedUser(securityToken).getName());
        authorizedUsers.remove(securityToken);
        sessionRepository.deleteBySecurityToken(securityToken);
    }
}

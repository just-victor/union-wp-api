package chk.union.wp.security;

import chk.union.wp.common.exception.ApiException;
import chk.union.wp.common.exception.UnauthorizedException;
import chk.union.wp.common.response.ErrorResponse;
import chk.union.wp.repostiory.SessionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.StringUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    public static final String HEADER_X_SECURITY_TOKEN = "X-Security-Token";
    private static final String AUTH = "/api/auth/";
    private static final String REGISTER = "/api/users";
    private static final String EVENTS = "/api/events";

    private final SessionRepository sessionRepository;
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        try {
            validateUserAuthentication(request);
            filterChain.doFilter(request, response);
        } catch (ApiException e) {
            response.setStatus(e.getStatus().value());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.getWriter().write(convertObjectToJson(e));
        }
    }

    private String convertObjectToJson(final ApiException exception) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        ErrorResponse payload = new ErrorResponse();

        payload.setCode(exception.getCode());
        payload.setMessage(exception.getMessage());
        payload.setStatus(exception.getStatus());

        return mapper.writeValueAsString(payload);
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        return requestURI.contains(AUTH)
                || requestURI.contains(REGISTER)
                || (HttpMethod.GET.matches(method) && requestURI.contains(EVENTS));
    }

    private void validateUserAuthentication(final HttpServletRequest request) {
        String securityToken = request.getHeader(HEADER_X_SECURITY_TOKEN);
        if (isEmpty(securityToken)) {
            throw new UnauthorizedException("User is not authorized");
        }

        sessionRepository.findFirstBySecurityToken(securityToken)
                .orElseThrow(() -> new UnauthorizedException("User is not authorized wrong security token"));

    }
}

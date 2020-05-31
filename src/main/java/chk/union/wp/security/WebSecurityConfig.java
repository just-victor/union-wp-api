package chk.union.wp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static chk.union.wp.common.exception.UnauthorizedException.NOT_AUTHENTICATED_USER;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String AUTH = "/api/auth/**";
    private static final String REGISTER = "/api/users";

    private final SecurityFilter securityFilter;
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .anyRequest()
                .permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }

//    @Override
//    public void configure(final WebSecurity web) {
//        web.ignoring().antMatchers(AUTH, REGISTER);
//    }

    private void http401UnauthorizedEntryPoint(final HttpServletRequest request, final HttpServletResponse response,
                                               final AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, NOT_AUTHENTICATED_USER);
    }
}

package chk.union.wp.serivce;

import chk.union.wp.common.exception.NotFoundException;
import chk.union.wp.dto.UserDto;
import chk.union.wp.entity.Session;
import chk.union.wp.entity.User;
import chk.union.wp.mapper.UserMapper;
import chk.union.wp.repostiory.SessionRepository;
import chk.union.wp.repostiory.UserRepository;
import chk.union.wp.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SessionRepository sessionRepository;

    public User getById(final Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public User register(final UserDto userDto, final String authoriztion) {
        User user = userMapper.toEntity(userDto, new User());

        PasswordUtils.generateUserPassword(user, authoriztion);

        return userRepository.save(user);
    }

    public Optional<User> findByTelephone(final String telephone) {
        return userRepository.findByTelephone(telephone);
    }

    public UserDto getUserData(final String token) {
        Session session = sessionRepository.findFirstBySecurityToken(token).orElseThrow(NotFoundException::new);

        return userMapper.toDto(session.getUser());
    }
}

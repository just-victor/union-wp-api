package chk.union.wp.serivce;

import chk.union.wp.common.exception.ConflictException;
import chk.union.wp.common.exception.ForbiddenException;
import chk.union.wp.common.exception.NotFoundException;
import chk.union.wp.dto.UserDto;
import chk.union.wp.entity.User;
import chk.union.wp.mapper.UserMapper;
import chk.union.wp.repostiory.UserRepository;
import chk.union.wp.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    public User getById(final Long id) {
        LOG.info("Find user by id: {}", id);
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public UserDto register(final UserDto userDto, final String authoriztion) {
        LOG.info("Try to register new user, name: {}, telephone: {}", userDto.getName(), userDto.getTelephone());
        Optional<User> userOpt = userRepository.findByTelephone(userDto.getTelephone());
        if (userOpt.isPresent()) {
            throw new ConflictException("Номер телефона уже зарегистрирован");
        }

        User user = userMapper.toEntity(userDto, new User());

        PasswordUtils.generateUserPassword(user, authoriztion);

        User registeredUser = userRepository.save(user);

        LOG.info("new user registered, id: {}, name: {}, telephone: {}", registeredUser.getId(), userDto.getName(), userDto.getTelephone());

        return userMapper.toDto(registeredUser);
    }

    public Optional<User> findByTelephone(final String telephone) {
        return userRepository.findByTelephone(telephone);
    }

    public UserDto getUserData() {
        return authService.getCurrentUserDto();
    }

    public List<UserDto> search(final String name) {
        if (!authService.getCurrentUserDto().getIsAdmin()) {
            throw new ForbiddenException();
        }

        LOG.info("Find users by name where name like '%{}'", name);
        List<User> users = userRepository.findAllByNameStartingWithIgnoreCase(name);
        LOG.info("Found {} users", users.size());

        return userMapper.toDtos(users);
    }

    public List<UserDto> getAll() {
        return userMapper.toDtos(userRepository.findAll());
    }
}

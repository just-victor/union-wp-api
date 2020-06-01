package chk.union.wp.mapper;

import chk.union.wp.dto.UserDto;
import chk.union.wp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {
    User toEntity(UserDto userDto, @MappingTarget User user);
    UserDto toDto(User user);

    List<UserDto> toDtos(Collection<User> users);
}

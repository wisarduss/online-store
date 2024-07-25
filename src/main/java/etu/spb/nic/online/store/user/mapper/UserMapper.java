package etu.spb.nic.online.store.user.mapper;

import etu.spb.nic.online.store.user.dto.UserDto;
import etu.spb.nic.online.store.user.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User userDtoToUser(UserDto userCreateDto) {

        return User.builder()
                .id(userCreateDto.getId())
                .name(userCreateDto.getName())
                .surname(userCreateDto.getSurname())
                .email(userCreateDto.getEmail())
                .password(userCreateDto.getPassword())
                .build();
    }

    public static UserDto userToUserDto(User user) {

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}

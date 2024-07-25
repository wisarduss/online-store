package etu.spb.nic.online.store.common.util;


import etu.spb.nic.online.store.user.dto.UserDto;
import etu.spb.nic.online.store.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userCreateDto = (UserDto) o;

        userService.create(userCreateDto, userCreateDto.getEmail());
    }

}

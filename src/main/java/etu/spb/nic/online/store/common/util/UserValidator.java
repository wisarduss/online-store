package etu.spb.nic.online.store.common.util;


import etu.spb.nic.online.store.common.exception.AlreadyExistException;
import etu.spb.nic.online.store.user.dto.UserDto;
import etu.spb.nic.online.store.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userCreateDto = (UserDto) o;

        if (userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new AlreadyExistException("Такой пользователь уже зарегистрирован");
        }
    }

}

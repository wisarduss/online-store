package etu.spb.nic.online.store.authentication.controller;

import etu.spb.nic.online.store.authentication.dto.AuthenticationDto;
import etu.spb.nic.online.store.authentication.service.AuthenticationService;
import etu.spb.nic.online.store.common.exception.BadRegistrationException;
import etu.spb.nic.online.store.common.util.JWTUtil;
import etu.spb.nic.online.store.common.util.UserValidator;
import etu.spb.nic.online.store.user.dto.UserDto;
import etu.spb.nic.online.store.user.mapper.UserMapper;
import etu.spb.nic.online.store.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserValidator userValidator;
    private final AuthenticationService authenticationService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public String performRegistration(@RequestBody @Valid UserDto userCreateDto,
                                      BindingResult bindingResult) {

        userValidator.validate(userCreateDto, bindingResult);

        User user = UserMapper.userDtoToUser(userCreateDto);

        if (bindingResult.hasErrors()) {
            throw new BadRegistrationException("Пользователь не прошел проверку регистрации!");
        }

        authenticationService.register(user);

        String token = jwtUtil.generateToken(user.getEmail());
        log.debug("Пользователь зарегистрирован {} JWTToken выдан", userCreateDto);
        return token;
    }

    @PostMapping("/login")
    public String performLogin(@RequestBody @Valid AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(), authenticationDto.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new BadRegistrationException("Incorrect credentials");
        }

        String token = jwtUtil.generateToken(authenticationDto.getEmail());
        log.debug("Пользователь прошел аутентификацию, JWTToken получен");
        return token;
    }


}

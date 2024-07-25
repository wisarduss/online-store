package etu.spb.nic.online.store.authentication.controller;

import etu.spb.nic.online.store.authentication.dto.AuthenticationDto;
import etu.spb.nic.online.store.authentication.service.AuthenticationService;
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
import java.util.Collections;
import java.util.Map;


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
    public Map<String, String> performRegistration(@RequestBody @Valid UserDto userCreateDto,
                                                   BindingResult bindingResult) {

        userValidator.validate(userCreateDto, bindingResult);

        User user = UserMapper.userDtoToUser(userCreateDto);

        if (bindingResult.hasErrors()) {
            return Collections.singletonMap("message", "Ошибка!");
        }

        authenticationService.register(user);

        String token = jwtUtil.generateToken(user.getEmail());
        log.debug("Получен POST запрос на регистрацию пользователя {}", userCreateDto);
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(), authenticationDto.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Collections.singletonMap("message", "Incorrect credentials");
        }

        String token = jwtUtil.generateToken(authenticationDto.getEmail());
        log.debug("Получен POST запрос на аутентификацию пользователя");
        return Collections.singletonMap("jwt-token", token);
    }


}

package etu.spb.nic.online.store.user.service;

import etu.spb.nic.online.store.authentication.security.PersonDetails;
import etu.spb.nic.online.store.common.exception.AlreadyExistException;
import etu.spb.nic.online.store.user.dto.UserDto;
import etu.spb.nic.online.store.user.mapper.UserMapper;
import etu.spb.nic.online.store.user.model.User;
import etu.spb.nic.online.store.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Пользователь с email = " + s + " не найден");
        }

        return new PersonDetails(user.get());
    }

    @Override
    public UserDetails create(UserDto userDto, String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            throw new AlreadyExistException("Пользователь с таким Email уже существует");
        }

        User saveUser = UserMapper.userDtoToUser(userDto);

        return new PersonDetails(saveUser);
    }

}

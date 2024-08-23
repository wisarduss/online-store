package etu.spb.nic.online.store.user.service;

import etu.spb.nic.online.store.authentication.security.PersonDetails;
import etu.spb.nic.online.store.common.exception.NotOwnerException;
import etu.spb.nic.online.store.user.model.User;
import etu.spb.nic.online.store.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public User getAuthenticatedUser() {
        PersonDetails principal = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(principal.getUsername());
        if (!user.isPresent()) {
            throw new NotOwnerException("не пользователь");
        }

        return user.get();
    }

}

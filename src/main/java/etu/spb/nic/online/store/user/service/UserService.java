package etu.spb.nic.online.store.user.service;

import etu.spb.nic.online.store.user.dto.UserDto;
import etu.spb.nic.online.store.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    User getAuthenticatedUser();

    UserDetails create(UserDto userDto, String email);

}

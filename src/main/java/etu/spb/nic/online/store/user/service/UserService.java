package etu.spb.nic.online.store.user.service;

import etu.spb.nic.online.store.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails create(UserDto userDto, String email);

}

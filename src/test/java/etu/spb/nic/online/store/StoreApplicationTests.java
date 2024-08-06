package etu.spb.nic.online.store;

import etu.spb.nic.online.store.authentication.config.JWTFilter;
import etu.spb.nic.online.store.authentication.controller.AuthController;
import etu.spb.nic.online.store.authentication.service.AuthenticationService;
import etu.spb.nic.online.store.common.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
class StoreApplicationTests {

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private AuthController authController;

    @Test
    void contextLoads() {
    }

}

package etu.spb.nic.online.store.authentication.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
public class AuthenticationDto {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 2, max = 18)
    private String password;
}

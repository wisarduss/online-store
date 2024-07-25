package etu.spb.nic.online.store.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
public class ErrorResponse {

    private String error;
}

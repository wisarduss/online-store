package etu.spb.nic.online.store.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Ошибка валидации данных из запроса.")
                .build();
        log.debug("{}: {}", MethodArgumentNotValidException.class.getSimpleName(),
                e.getFieldError().getDefaultMessage());

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdNotFoundException.class)
    public ErrorResponse handleValidationExceptions(IdNotFoundException ex) {
        log.debug("Получен статус 404 not found {}", ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleObjectDoesNotExistException(final AlreadyExistException e) {
        log.debug("Получен статус 500 internal server error  {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(LassThenZeroException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleObjectDoesNotExistException(final LassThenZeroException e) {
        log.debug("Получен статус 500 internal server error  {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(NotOwnerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleObjectDoesNotExistException(final NotOwnerException e) {
        log.debug("Получен статус 400 bad request {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }
}

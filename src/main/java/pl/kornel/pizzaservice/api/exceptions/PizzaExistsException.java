package pl.kornel.pizzaservice.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "such pizza already exists")  // 400
public class PizzaExistsException extends RuntimeException {
    public PizzaExistsException(String name) {
        super(String.format("Pizza named %s already exists", name));
    }
}

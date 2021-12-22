package pl.kornel.pizzaservice.infrastructure.pizza;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such pizza exists")  // 404
public class PizzaDoesNotExistsException extends RuntimeException {
    public PizzaDoesNotExistsException(String name) {
        super(String.format("Pizza named %s does not exist", name));
    }
}

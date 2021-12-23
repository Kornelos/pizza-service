package pl.kornel.pizzaservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.infrastructure.pizza.PizzaJson;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatePizzaTest extends BasePizzaTest {

    @Test
    void shouldCreateNewPizza() throws JsonProcessingException {
        // given
        PizzaJson newPizza = new PizzaJson("Margarita", 32, 3000, List.of(Ingredient.Cheese, Ingredient.TomatoSauce));

        // when
        var response = testRestTemplate.exchange("/pizzas", HttpMethod.PUT, createPizzaEntity(newPizza), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // cleanup
        pizzaRepository.deleteByName(newPizza.getName());
    }

    @Test
    void shouldReturnBadRequestIfPizzaAlreadyExists() throws JsonProcessingException {
        // given
        PizzaJson newPizza = new PizzaJson("Margarita", 32, 3000, List.of(Ingredient.Cheese, Ingredient.TomatoSauce));
        pizzaRepository.save(PizzaEntity.fromPizza(newPizza));

        // when
        var response = testRestTemplate.exchange("/pizzas", HttpMethod.PUT, createPizzaEntity(newPizza), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        // cleanup
        pizzaRepository.deleteByName(newPizza.getName());
    }
}

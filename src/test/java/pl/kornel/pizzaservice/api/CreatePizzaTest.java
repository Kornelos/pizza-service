package pl.kornel.pizzaservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.domain.pizza.Pizza;
import pl.kornel.pizzaservice.infrastructure.pizza.PizzaJson;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatePizzaTest extends BasePizzaTest {

    @Test
    void shouldCreateNewPizza() throws JsonProcessingException {
        // given
        Pizza newPizza = new Pizza("Margarita", 32, 3000, List.of(Ingredient.Cheese, Ingredient.TomatoSauce));

        // when
        var response = testRestTemplate.exchange("/pizzas", HttpMethod.PUT, createPizzaEntity(PizzaJson.fromPizza(newPizza)), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // cleanup
        pizzaRepository.deleteByName(newPizza.getName());
    }

    @Test
    void shouldReturnBadRequestIfPizzaAlreadyExists() throws JsonProcessingException {
        // given
        Pizza newPizza = new Pizza("Margarita", 32, 3000, List.of(Ingredient.Cheese, Ingredient.TomatoSauce));
        pizzaRepository.save(PizzaEntity.fromPizza(newPizza));

        // when
        var response = testRestTemplate.exchange("/pizzas", HttpMethod.PUT, createPizzaEntity(PizzaJson.fromPizza(newPizza)), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        // cleanup
        pizzaRepository.deleteByName(newPizza.getName());
    }
}

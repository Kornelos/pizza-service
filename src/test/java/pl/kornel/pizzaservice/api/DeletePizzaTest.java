package pl.kornel.pizzaservice.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.domain.pizza.Pizza;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeletePizzaTest extends BasePizzaTest {
    @Test
    void shouldDeletePizza() {
        // given
        Pizza pizzaForRemoval = new Pizza("PizzaForRemoval", 32, 3000, List.of(Ingredient.Cheese, Ingredient.TomatoSauce));
        addPizza(pizzaForRemoval);
        assertThat(pizzaRepository.existsByName(pizzaForRemoval.getName())).isTrue();
        // when
        var response = testRestTemplate.exchange(String.format("/pizzas/%s", pizzaForRemoval.getName()), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(pizzaRepository.existsByName(pizzaForRemoval.getName())).isFalse();
    }

    @Test
    void shouldReturnNotFoundIfPizzaDoesNotExist() {
        Pizza pizzaForRemoval = new Pizza("PizzaForRemoval", 32, 3000, List.of(Ingredient.Cheese, Ingredient.TomatoSauce));
        assertThat(pizzaRepository.existsByName(pizzaForRemoval.getName())).isFalse();

        // when
        var response = testRestTemplate.exchange(String.format("/pizzas/%s", pizzaForRemoval.getName()), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

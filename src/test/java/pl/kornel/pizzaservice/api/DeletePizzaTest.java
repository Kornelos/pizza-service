package pl.kornel.pizzaservice.api;

import org.junit.jupiter.api.Test;
import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.domain.pizza.Pizza;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeletePizzaTest extends BasePizzaTest {
    @Test
    void shouldDeletePizza() {
        // given
        Pizza pizzaForRemoval = new Pizza("PizzaForRemoval", 32, 3000, List.of(Ingredient.Cheese, Ingredient.TomatoSauce));
        addPizza(pizzaForRemoval);
        assertTrue(pizzaRepository.existsByName(pizzaForRemoval.getName()));

        // when
        testRestTemplate.delete(String.format("/pizzas/%s", pizzaForRemoval.getName()));

        // then
        assertFalse(pizzaRepository.existsByName(pizzaForRemoval.getName()));
    }
}

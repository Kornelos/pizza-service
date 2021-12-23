package pl.kornel.pizzaservice.api;

import org.junit.jupiter.api.Test;
import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.domain.pizza.Pizza;
import pl.kornel.pizzaservice.infrastructure.pizza.PizzaJson;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetPizzaTest extends BasePizzaTest {

    @Test
    void shouldGetListOfPizzas() {
        // given
        Pizza pizza1 = new Pizza("Pizza1", 30, 4000, List.of(Ingredient.Cheese));
        addPizza(pizza1);
        Pizza hawaii = new Pizza("Hawaii", 42, 5000, List.of(Ingredient.Cheese, Ingredient.Pineapple, Ingredient.Ham));
        addPizza(hawaii);

        // when
        List<PizzaJson> responseBody = List.of(testRestTemplate.getForEntity("/pizzas", PizzaJson[].class).getBody());

        // then
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertTrue(responseBody.get(0).getName().equals(pizza1.getName()) || responseBody.get(0).getName().equals(hawaii.getName()));

        // cleanup
        pizzaRepository.deleteByName(pizza1.getName());
        pizzaRepository.deleteByName(hawaii.getName());
    }
}

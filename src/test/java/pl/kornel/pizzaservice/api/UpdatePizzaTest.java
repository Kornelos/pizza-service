package pl.kornel.pizzaservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.domain.pizza.Pizza;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class UpdatePizzaTest extends BasePizzaTest {
    @Test
    void shouldUpdateExistingPizza() throws JsonProcessingException {
        // given
        Pizza pizza = new Pizza("PizzaForUpdate", 28, 5231, List.of(Ingredient.Cheese, Ingredient.TomatoSauce, Ingredient.Mushroom));
        addPizza(pizza);
        assertTrue(pizzaRepository.existsByName(pizza.getName()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        Pizza pizzaUpdate = new Pizza(pizza.getName(), 42, 6000, List.of(Ingredient.Pineapple, Ingredient.Cheese));
        String pizzaUpdateJsonString = objectMapper.writeValueAsString(pizzaUpdate);
        HttpEntity<String> entity = new HttpEntity<>(pizzaUpdateJsonString, headers);

        // when
        var result = testRestTemplate.exchange("/pizzas", HttpMethod.POST, entity, String.class);

        // then
        assertTrue(pizzaRepository.existsByName(pizza.getName()));
        var updated = pizzaRepository.findByName(pizza.getName()).toPizza();
        assertEquals(updated.getPrice(), pizzaUpdate.getPrice());
        assertEquals(updated.getSize(), pizzaUpdate.getSize());
        assertTrue(updated.getIngredients().containsAll(pizzaUpdate.getIngredients()));

        // cleanup
        pizzaRepository.deleteByName(pizza.getName());
    }
}

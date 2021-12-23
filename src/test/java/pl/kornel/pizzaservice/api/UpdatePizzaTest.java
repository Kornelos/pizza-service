package pl.kornel.pizzaservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.domain.pizza.Pizza;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class UpdatePizzaTest extends BasePizzaTest {
    @Test
    void shouldUpdateExistingPizza() throws JsonProcessingException {
        // given
        Pizza pizza = new Pizza("PizzaForUpdate", 28, 5231, List.of(Ingredient.Cheese, Ingredient.TomatoSauce, Ingredient.Mushroom));
        addPizza(pizza);
        assertThat(pizzaRepository.existsByName(pizza.getName())).isTrue();
        Pizza pizzaUpdate = new Pizza(pizza.getName(), 42, 6000, List.of(Ingredient.Pineapple, Ingredient.Cheese));

        // when
        testRestTemplate.exchange("/pizzas", HttpMethod.POST, createUpdatePizzaEntity(pizzaUpdate), String.class);

        // then
        assertThat(pizzaRepository.existsByName(pizza.getName())).isTrue();
        var updated = pizzaRepository.findByName(pizza.getName()).toPizza();
        assertThat(updated.getPrice()).isEqualTo(pizzaUpdate.getPrice());
        assertThat(updated.getSize()).isEqualTo(pizzaUpdate.getSize());
        assertThat(updated.getIngredients().containsAll(pizzaUpdate.getIngredients())).isTrue();

        // cleanup
        pizzaRepository.deleteByName(pizza.getName());
    }

    @Test
    void idShouldNotChangeAfterUpdate() throws JsonProcessingException {
        Pizza pizza = new Pizza("PizzaForUpdate", 28, 5231, List.of(Ingredient.Cheese, Ingredient.TomatoSauce, Ingredient.Mushroom));
        addPizza(pizza);
        PizzaEntity beforeUpdate = pizzaRepository.findByName(pizza.getName());
        Pizza pizzaUpdate = new Pizza("PizzaForUpdate", 100, 5231, List.of(Ingredient.Pineapple));

        // when
        testRestTemplate.exchange("/pizzas", HttpMethod.POST, createUpdatePizzaEntity(pizzaUpdate), String.class);

        // then
        PizzaEntity updatedPizza = pizzaRepository.findByName(pizza.getName());
        assertThat(updatedPizza.getId()).isEqualTo(beforeUpdate.getId());
        assertThat(updatedPizza.getSize()).isEqualTo(100);
        assertThat(updatedPizza.getIngredients()).containsAll(pizzaUpdate.getIngredients());
        assertThat(updatedPizza.getIngredients()).doesNotContainAnyElementsOf(pizza.getIngredients());

        // cleanup
        pizzaRepository.deleteByName(pizza.getName());
    }

    @Test
    void shouldReturnNotFoundIfPizzaDoesNotExist() throws JsonProcessingException {
        // given
        Pizza nonExisting = new Pizza("non-existing",33,3425,List.of(Ingredient.TomatoSauce));
        assertThat(pizzaRepository.existsByName(nonExisting.getName())).isFalse();

        // when
        var result = testRestTemplate.exchange("/pizzas", HttpMethod.POST, createUpdatePizzaEntity(nonExisting), String.class);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private HttpEntity<String> createUpdatePizzaEntity(Pizza pizzaUpdate) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        String pizzaUpdateJsonString = objectMapper.writeValueAsString(pizzaUpdate);
        return new HttpEntity<>(pizzaUpdateJsonString, headers);
    }
}

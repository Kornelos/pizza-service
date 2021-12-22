package pl.kornel.pizzaservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.domain.pizza.Pizza;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class CreatePizzaTest extends BasePizzaTest {

    @Test
    void shouldCreateNewPizza() throws JsonProcessingException {
        // given
        Pizza newPizza = new Pizza("Margarita", 32, 3000, List.of(Ingredient.Cheese, Ingredient.TomatoSauce));
        String pizzaJsonString = objectMapper.writeValueAsString(newPizza);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(pizzaJsonString, headers);

        // when
        var response = testRestTemplate.exchange("/pizzas", HttpMethod.PUT, entity, String.class);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // cleanup
        pizzaRepository.deleteByName(newPizza.getName());
    }
}

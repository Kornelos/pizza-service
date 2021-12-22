package pl.kornel.pizzaservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.domain.pizza.Pizza;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaEntity;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PizzaEndpointTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PizzaRepository pizzaRepository;


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
    }

    @Test
    void shouldGetListOfPizzas() {
        // given
        Pizza pizza1 = new Pizza("Pizza1", 30, 4000, List.of(Ingredient.Cheese));
        addPizza(pizza1);
        Pizza hawaii = new Pizza("Hawaii", 42, 5000, List.of(Ingredient.Cheese, Ingredient.Pineapple, Ingredient.Ham));
        addPizza(hawaii);

        // when
        List<Pizza> responseBody = List.of(testRestTemplate.getForEntity("/pizzas", Pizza[].class).getBody());

        // then
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertTrue(responseBody.get(0).getName().equals(pizza1.getName()) || responseBody.get(0).getName().equals(hawaii.getName()));

    }

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


    }

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


    private void addPizza(Pizza pizza) {
        pizzaRepository.save(PizzaEntity.fromPizza(pizza));
    }

}

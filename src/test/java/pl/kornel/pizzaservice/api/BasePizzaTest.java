package pl.kornel.pizzaservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.kornel.pizzaservice.domain.pizza.Pizza;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaEntity;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BasePizzaTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PizzaRepository pizzaRepository;

    protected void addPizza(Pizza pizza) {
        pizzaRepository.save(PizzaEntity.fromPizza(pizza));
    }

    protected HttpEntity<String> createPizzaEntity(Pizza pizza) throws JsonProcessingException {
        String pizzaJsonString = objectMapper.writeValueAsString(pizza);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(pizzaJsonString, headers);
        return new HttpEntity<>(pizzaJsonString, headers);
    }
}

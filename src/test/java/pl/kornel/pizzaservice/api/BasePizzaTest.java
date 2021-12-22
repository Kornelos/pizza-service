package pl.kornel.pizzaservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.kornel.pizzaservice.domain.pizza.Pizza;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaEntity;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaRepository;

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
}

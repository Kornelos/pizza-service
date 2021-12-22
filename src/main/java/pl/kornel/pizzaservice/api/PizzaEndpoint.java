package pl.kornel.pizzaservice.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kornel.pizzaservice.domain.pizza.Pizza;
import pl.kornel.pizzaservice.domain.pizza.PizzaService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/pizzas")
public class PizzaEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(PizzaEndpoint.class);

    private final PizzaService pizzaService;

    @Autowired
    public PizzaEndpoint(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Pizza>> getAllPizzas() {
        return ResponseEntity.ok(pizzaService.getAll());
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewPizza(@RequestBody Pizza pizza) {
        logger.info("Got create pizza request: [{}]", pizza);
        pizzaService.addPizza(pizza);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deletePizzaByName(@PathVariable String name) {
        logger.info("Got delete pizza request for name: [{}]", name);
        pizzaService.deletePizzaByName(name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<?> updatePizza(@RequestBody Pizza pizza) {
        logger.info("Got update pizza request: [{}]", pizza);
        pizzaService.updatePizza(pizza);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

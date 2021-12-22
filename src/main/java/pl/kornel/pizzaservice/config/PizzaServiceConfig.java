package pl.kornel.pizzaservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kornel.pizzaservice.domain.pizza.PizzaService;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaRepository;
import pl.kornel.pizzaservice.infrastructure.pizza.InMemoryPizzaService;

@Configuration
public class PizzaServiceConfig {

    @Bean
    public PizzaService pizzaService(PizzaRepository pizzaRepository){
        return new InMemoryPizzaService(pizzaRepository);
    }
}

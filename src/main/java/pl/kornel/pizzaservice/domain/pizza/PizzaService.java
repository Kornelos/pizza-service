package pl.kornel.pizzaservice.domain.pizza;

import pl.kornel.pizzaservice.infrastructure.pizza.PizzaJson;

import java.util.List;

public interface PizzaService {
    List<PizzaJson> getAll();

    void addPizza(PizzaJson pizza);

    void deletePizzaByName(String name);

    void updatePizza(PizzaJson pizza);
}

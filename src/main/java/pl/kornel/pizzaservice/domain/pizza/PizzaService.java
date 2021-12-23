package pl.kornel.pizzaservice.domain.pizza;

import java.util.List;

public interface PizzaService {
    List<Pizza> getAll();

    void addPizza(Pizza pizza);

    void deletePizzaByName(String name);

    void updatePizza(Pizza pizza);
}

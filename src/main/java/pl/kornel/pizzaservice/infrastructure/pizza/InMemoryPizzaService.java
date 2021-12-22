package pl.kornel.pizzaservice.infrastructure.pizza;

import pl.kornel.pizzaservice.domain.pizza.Pizza;
import pl.kornel.pizzaservice.domain.pizza.PizzaService;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaEntity;
import pl.kornel.pizzaservice.infrastructure.persistence.PizzaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class InMemoryPizzaService implements PizzaService {
    final private PizzaRepository pizzaRepository;

    public InMemoryPizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }


    @Override
    public List<Pizza> getAll() {
        return pizzaRepository.findAll().stream().map(PizzaEntity::toPizza).collect(Collectors.toList());
    }

    @Override
    public void addPizza(Pizza pizza) {
        if (pizzaRepository.existsByName(pizza.getName())) {
            throw new PizzaExistsException(pizza.getName());
        } else {
            pizzaRepository.save(PizzaEntity.fromPizza(pizza));
        }
    }

    @Override
    public void deletePizzaByName(String name) {
        if (pizzaRepository.existsByName(name)) {
            pizzaRepository.deleteByName(name);
        } else {
            throw new PizzaDoesNotExistsException(name);
        }
    }

    @Override
    public void updatePizza(Pizza pizza) {
        deletePizzaByName(pizza.getName());
        addPizza(pizza);
    }
}

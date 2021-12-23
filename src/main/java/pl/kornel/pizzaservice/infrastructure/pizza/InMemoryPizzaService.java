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
        if (!pizzaRepository.existsByName(pizza.getName())) {
            throw new PizzaDoesNotExistsException(pizza.getName());
        }
        PizzaEntity existingPizza = pizzaRepository.findByName(pizza.getName());
        pizzaRepository.save(createUpdatedPizza(existingPizza, pizza)); // spring jpa performs merge on save operation
    }

    private PizzaEntity createUpdatedPizza(PizzaEntity existingPizza, Pizza pizzaUpdate) {
        PizzaEntity updated = new PizzaEntity(existingPizza);
        if (pizzaUpdate.getPrice() != existingPizza.getPrice()) {
            updated.setPrice(pizzaUpdate.getPrice());
        }
        if (pizzaUpdate.getSize() != existingPizza.getSize()) {
            updated.setSize(pizzaUpdate.getSize());
        }
        if (!existingPizza.getIngredients().containsAll(pizzaUpdate.getIngredients())) {
            updated.setIngredients(pizzaUpdate.getIngredients());
        }
        return updated;
    }

}

package pl.kornel.pizzaservice.infrastructure.pizza;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.domain.pizza.Pizza;

import java.util.List;

public class PizzaJson {
    private String name;
    private int size;
    private int price;
    private List<Ingredient> ingredients;

    @JsonCreator()
    public PizzaJson(@JsonProperty("name") String name,
                     @JsonProperty("size") int size,
                     @JsonProperty("price") int price,
                     @JsonProperty("ingredients") List<Ingredient> ingredients) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Pizza toPizza() {
        return new Pizza(getName(), getSize(), getPrice(), getIngredients());
    }
    public static PizzaJson fromPizza(Pizza pizza) {
        return new PizzaJson(pizza.getName(), pizza.getSize(), pizza.getPrice(), pizza.getIngredients());
    }
}

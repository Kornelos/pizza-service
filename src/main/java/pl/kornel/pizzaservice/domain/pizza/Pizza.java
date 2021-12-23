package pl.kornel.pizzaservice.domain.pizza;

import java.util.Collections;
import java.util.List;

public class Pizza {
    private final String name;
    private final int size;
    private final int price;
    private final List<Ingredient> ingredients;

    public Pizza(String name, int size, int price, List<Ingredient> ingredients) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getPrice() {
        return price;
    }

    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", ingredients=" + ingredients +
                '}';
    }
}

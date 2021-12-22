package pl.kornel.pizzaservice.domain.pizza;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Pizza {
    String name;
    int size;
    int price;
    List<Ingredient> ingredients;

    @JsonCreator()
    public Pizza(@JsonProperty("name") String name,
                 @JsonProperty("size") int size,
                 @JsonProperty("price") int price,
                 @JsonProperty("ingredients") List<Ingredient> ingredients) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setIngredients(List<Ingredient> ingredients) {
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
        return ingredients;
    }
}

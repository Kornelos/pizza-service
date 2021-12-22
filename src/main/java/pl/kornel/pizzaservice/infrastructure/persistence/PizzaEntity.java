package pl.kornel.pizzaservice.infrastructure.persistence;

import pl.kornel.pizzaservice.domain.pizza.Ingredient;
import pl.kornel.pizzaservice.domain.pizza.Pizza;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "pizzas")
public class PizzaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private int size;

    @Column(name = "price")
    private int price;

    @Column(name = "ingredients")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Ingredient> ingredients;

    public PizzaEntity(String name, int size, int price, List<Ingredient> ingredients) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.ingredients = ingredients;
    }

    public PizzaEntity() {

    }

    public static PizzaEntity fromPizza(Pizza pizza) {
        return new PizzaEntity(pizza.getName(),pizza.getSize(),pizza.getPrice(),pizza.getIngredients());
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

    public Pizza toPizza(){
        return new Pizza(name,size,price,ingredients);
    }


}

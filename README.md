# Pizza Service

## Api reference

| Endpoint       | Method | Body       | Description                |
|----------------|--------|------------|----------------------------|
| /pizzas        | GET    | -          | Fetches all pizzas         |
| /pizzas        | PUT    | Pizza JSON | Adds new pizza             |
| /pizzas/{name} | DELETE | -          | Removes pizza named {name} |
| /pizzas        | POST   | Pizza JSON | Updates existing pizza     |

## Model
The size of the pizza is expressed in cm and the cost in pennies.
```json
{
  "name": "pizza-name",
  "size": 32,
  "price": 4000,
  "ingredients": ["Ham", "Cheese"]
}

```
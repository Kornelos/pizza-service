# Pizza Service

Service uses in memory SQL database H2 and Spring JPA.
Database credentials are in `application.yaml`.
DB UI can be found at `http://localhost:8080/h2-console`
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
Possible ingredients are listed in enum Ingredients,  I've assumed that they
won't change often. If that is not the case I would use list in properties.
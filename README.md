# Open Fridge Recipe (OFR)

## How tow install
1. You need a database

2. Clone the repo

   `git clone https://github.com/Jeido-RougeTerre/OpenFridgeRecipe`

3. Create in `./src/main/resources/` the file `application-secret.properties` and put your credential there :
   ```properties
   spring.datasource.url=jdbc:{{YOUR_DATABASE_DRIVER}}://{{YOUR_DATABASE_URL}}:{{YOUR_DATABASE_PORT}}/{{YOUR_DATABASE_NAME}}
   spring.datasource.username={{YOUR_DATABASE_USERNAME}}
   spring.datasource.password={{YOUR_DATABASE_PASSWORD}}
   ```
4. By default, the API is host on `https://localhost:8080` if you want to change the port add this line to the `application-secret.properties`
   ```properties
   spring.datasource.url=jdbc:{{YOUR_DATABASE_DRIVER}}://{{YOUR_DATABASE_URL}}:{{YOUR_DATABASE_PORT}}/{{YOUR_DATABASE_NAME}}
   spring.datasource.username={{YOUR_DATABASE_USERNAME}}
   spring.datasource.password={{YOUR_DATABASE_PASSWORD}}
   server.port={{YOUR_PORT}}
   ```
5. You can now launch your project all the end point are listed in Api part below

   ``mvn spring-boot:run``

## API

### `/api`
- GET

  *return a response if the API is up*
   ``
   Fridge API Working
   ``
### USER `/api/users`
- GET

  *return the list of users*
  ```json
     [
        {
          "id": "40a10087-ce87-4b26-bd34-ed13230f6640",
          "name": "Admin",
          "surname": "Supreme",
          "favoriteRecipes": [],
          "email": "no@m.com",
          "admin": true
        },
        {
         "id": "40a14487-bc78-4b26-bd34-ed13230f6640",
          "name": "John",
          "surname": "Doe",
          "favoriteRecipes": [],
          "email": "jo.do@m.com",
          "admin": false
      }
     ]
  ```
#### `/api/users/register`
  - POST ({json})

```json
{
  "email": "",
  "password": "",
  "name": "",
  "surname": ""
}
```

#### `/api/users/login`
- POST ({json}) 
```json
{
  "email": "",
  "password" : ""
}
```
*return `401` if there is no user for that email or email and password doesn't correspond*

***if email and password DOES correspond** then return a json containing user's details*

#### `/api/user/{{id}}`
- GET

  *return user details*
- PUT ({json})

*return user updated*
```json
{
  "email": "",
  "password": "",
  "name": "",
  "surname": "",
  "favoriteRecipes": []
}
```

- DELETE

  *Delete user*

### FRIDGE 


#### `/api/fridges/user/{{userId}}`
- GET

  *returns fridge from user's id*

#### `/api/fridges/{{id}}`
- GET

  *returns fridge*

#### `/api/fridges/{{id}}/ingredients`
- GET

  *returns all ingredients in the fridge*

- DELETE

  *delete all ingredients in the fridge*

#### `/api/fridges/{{id}}/ingredients/{{code}}`
- POST

  *add an ingredient to the fridge*

- DELETE

  *delete a specific ingredient in the fridge*

#### `/api/fridges/{{id}}/recipes`
- GET

  *returns a suggested list of recipes from the ingredient list of the fridge*

#### `/api/fridges/{{id}}/tags`
- GET

  *returns a list of tags of all ingredients within the fridge*

### INGREDIENTS

#### `/api/ingredients/{{code}}`
- GET

  *returns the ingredient*


#### `/api/ingredients/tags/{{tag1[,tag2,tag3,...]}}`
- GET

  *returns a list of ingredient containing all the tags parsed*


#### `/api/ingredients/search/{{terms}}[/{{page}}]`
- GET

  *returns a Paginated Json*
  ```json
    {
    "searchedTerm": "ban",
    "page": 1,
    "prevPage": 0,
    "nextPage": 2,
    "pageSize": 50,
    "pageCount": 50,
    "count": 54,
    "totalPages": 2,
    "results": ["..."]
  }
  ```


#### `/api/ingredients/calories/{{calories} | {caloriesMin}_{caloriesMax}}`
- GET


### RECIPES `/api/recipe`
- GET

  *returns all recipes in database*

- POST 

```json
{
  "name": "Recipe name",
  "cutleryNb": 4,
  "ingredientsCode": ["3700278400881", "3770000535068"]
}
```


#### `/api/recipe/{{id}}`
- GET
- PUT
- DELETE


#### `/api/recipe/tag/{{tag}}`
- GET


#### `/api/recipe/name/{{name}}`
- GET


#### `/api/recipe/ingredient/{{code}}`
- GET

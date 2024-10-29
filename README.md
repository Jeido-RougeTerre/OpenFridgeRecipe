# Open Fridge Recipe (OFR)

## How tow install
1. You need a database


2. Clone the repo

   `git clone https://github.com/Jeido-RougeTerre/OpenFridgeRecipe`

3. Create in `./src/resources/` the file `application-secret.properties` and put your credential there :
   ```properties
   spring.datasource.url=jdbc:{{YOUR_DATABASE_DRIVER}}://{{YOUR_DATABASE_URL}}/{{YOUR_DATABASE_NAME}}
   spring.datasource.username={{YOUR_DATABASE_USERNAME}}
   spring.datasource.password={{YOUR_DATABASE_PASSWORD}}
   ```
4. By default the API is host on `https://localhost:8080` if you want to change the port add this line to the `application-secret.properties`
   ```properties
   spring.datasource.url=jdbc:{{YOUR_DATABASE_DRIVER}}://{{YOUR_DATABASE_URL}}/{{YOUR_DATABASE_NAME}}
   spring.datasource.username={{YOUR_DATABASE_USERNAME}}
   spring.datasource.password={{YOUR_DATABASE_PASSWORD}}
   server.port={{YOUR_PORT}}
   ```
5. You can now launch your project all the end point are listed in Api part below 

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

**W.I.P.**

### INGREDIENTS

**W.I.P.**

### RECIPES

**W.I.P.**

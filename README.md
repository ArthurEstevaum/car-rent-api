## Rodando o projeto
Instalando dependências e iniciando o projeto em http://localhost:8080
```bash
./gradlew clean build

./gradlew bootRun
```
**Observação:** ao final do comando *bootRun* o token JWT de autenticação aparecerá no console.

## Documentação da API

### Login e cadastro

Após realizar login, para acessar todas as rotas autenticadas, é necessário sempre entregar o token no header Authorization, no formato Bearer token.

#### Realiza o login e devolve o token de autenticação

```http
  POST /auth/login
```
Exemplo de uso:

```json
{
    "username": "example",
    "password": "example_password"
}
```
Exemplo de retorno:
```json
{
    "accessToken":
"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3NDIwNDA5NDgsImV4cCI6MTc0MjA0MjE3MywiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.4ypWkjNvmC_LIONbkhJz_JSk5H8wdS12tOjjw5gwH2c",
    "expiresIn": 300
}
```

### Carros

#### Retorna todos os carros cadastrados no sistema

```http
  GET /cars/
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `available` | `boolean` | **opcional**. Indica se a busca retornará apenas os carros disponíveis |

#### Retorna todos os carros de um modelo

```http
  GET /cars/models/${modelName}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `modelName`      | `string` | **Obrigatório**. O nome do modelo buscado |

#### Retorna todos os carros de um fabricante

```http
  GET /cars/manufacturers/${manufacturerName}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `manufacturerName`      | `string` | **Obrigatório**. O nome do fabricante |

#### Retorna todos os carros de uma categoria

```http
  GET /cars/categories/${categoryName}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `categoryName`      | `string` | **Obrigatório**. O nome da categoria

#### Retorna todos os carros de um ano

```http
  GET /cars/model-years/${modelYear}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `modelYear`      | `int` | **Obrigatório**. O valor do ano do modelo |

#### Registra um carro

```http
  POST /cars/
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `modelName`      | `Request body` | **Obrigatório**. Placa do carro, disponibilidade e nome do modelo do carro |

#### Atualiza um carro

```http
  PUT /cars/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `ID`      | `int` | **Obrigatório**. Id do carro a ser atualizado |

#### Exclui um carro

```http
  DELETE /cars/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `ID`      | `int` | **Obrigatório**. Id do carro a ser excluído |







## Diagrama de entidades ##

```mermaid
erDiagram
    RentingContract {
        int contractId
        date startDate
        date endDate
        double contractTotalPrice
        boolean active
        int carId FK
        int userId FK
    }

    Car {
        int id PK
        string licensePlate
        string manufacturer
        string model
        string category
        int modelYear
    }

    User {
        int id PK
        string username
        string email
        string phoneNumber
        string userType
    }

    RentingContract }o--|| Car : "Rented"
    RentingContract }o--|| User : "Tenant"
```

## Diagrama de entidades ##
erDiagram
    Contract {
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

    Contract }o--|| Car : "carInfo"
    Contract }o--|| User : "userInfo"

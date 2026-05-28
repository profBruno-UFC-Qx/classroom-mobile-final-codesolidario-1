## Diagrama de classes

```mermaid
classDiagram
    direction LR

    class User {
        +Int id
        +String firstname
        +String lastname
        +String email
        +String password
    }

    class Organization {
        +Int id
        +String name
        +String phoneNumber
        +String address
        +String observations
        +Int createBy
    }

    class Donation {
        +Int id
        +String imageUrl
        +String name
        +DonationCategory category
        +String description
        +Int quantity
        +Int organizationId
        +Int creatorId
        +DonationStatus status
        +Int beneficiaryId
    }

    class Beneficiary {
        +Int id
        +String name
        +String cpf
        +String phoneNumber
        +String address
        +Int organizationId
    }

    class DonationStatus {
        <<enumeration>>
        PENDING
        DELIVERED
        +String value
    }

    class DonationCategory {
        <<enumeration>>
        FOOD
        CLOTHING
        HYGIENE
        FURNITURE_AND_UTENSILS
        +String value
    }

    class NavigationItem {
        <<enumeration>>
        HOME
        DONATIONS
        BENEFICIARIES
        HISTORY
        PROFILE
        +String title
        +ImageVector icon
    }

    %% Relacionamentos e Vínculos
    User "1" --> "0..*" Organization : cria
    User "1" --> "0..*" Donation : registra
    Organization "1" --> "0..*" Donation : possui
    Organization "1" --> "0..*" Beneficiary : atende
    Donation "0..*" --> "0..1" Beneficiary : associada a
    Donation --> DonationStatus : usa
    Donation --> DonationCategory : usa

```
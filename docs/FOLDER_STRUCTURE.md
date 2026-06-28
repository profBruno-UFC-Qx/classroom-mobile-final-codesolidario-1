# Estrutura de Pastas

Abaixo está a organização do código-fonte da aplicação.

```text
app/src/main/java/com/example/givchurch
├── data
│   ├── local
│   │   ├── converters
│   │   │   └── Converters.kt
│   │   ├── dao
│   │   │   ├── BeneficiaryDao.kt
│   │   │   ├── DashboardDao.kt
│   │   │   ├── DonationDao.kt
│   │   │   └── dto
│   │   │       └── DonationMonthGroup.kt
│   │   ├── database
│   │   │   └── AppDatabase.kt
│   │   └── model
│   │       ├── Beneficiary.kt
│   │       ├── Donation.kt
│   │       └── enums
│   │           ├── DonationCategory.kt
│   │           └── DonationStatus.kt
│   ├── mapper
│   │   ├── BeneficiaryMapper.kt
│   │   ├── DonationMapper.kt
│   │   ├── UserMapper.kt
│   │   └── VerseMapper.kt
│   ├── mock
│   │   ├── BeneficiaryMockData.kt
│   │   ├── DonationMockData.kt
│   │   └── UserMockData.kt
│   ├── remote
│   │   ├── api
│   │   │   ├── model
│   │   │   │   └── VerseResponse.kt
│   │   │   ├── service
│   │   │   │   └── BibleWebService.kt
│   │   │   └── utils
│   │   │       ├── BibleStructure.kt
│   │   │       ├── BibleTextCleaner.kt
│   │   │       ├── BibleTextValidator.kt
│   │   │       ├── BibleUrlGenerator.kt
│   │   │       └── BookStructure.kt
│   │   └── firebase
│   │       ├── model
│   │       │   └── User.kt
│   │       └── service
│   │           ├── FirebaseAuthService.kt
│   │           └── FirebaseUserService.kt
│   └── repository
│       ├── AuthRepositoryImpl.kt
│       ├── BeneficiaryRepositoryImpl.kt
│       ├── BibleRepositoryImpl.kt
│       ├── DashboardRepositoryImpl.kt
│       ├── DonationRepositoryImpl.kt
│       ├── enums
│       │   └── SortDirection.kt
│       └── UserRepositoryImpl.kt
├── di
│   └── AppModule.kt
├── domain
│   ├── model
│   │   ├── Beneficiary.kt
│   │   ├── DashboardMetrics.kt
│   │   ├── Donation.kt
│   │   ├── enums
│   │   │   ├── DonationCategory.kt
│   │   │   └── DonationStatus.kt
│   │   ├── MonthlyDonation.kt
│   │   ├── User.kt
│   │   └── Verse.kt
│   └── repository
│       ├── AuthRepository.kt
│       ├── BeneficiaryRepository.kt
│       ├── BibleRepository.kt
│       ├── DashboardRepository.kt
│       ├── DonationRepository.kt
│       ├── enums
│       │   └── SortDirection.kt
│       └── UserRepository.kt
├── GivChurchApplication.kt
├── MainActivity.kt
├── navigation
│   ├── AuthenticatedNavGraph.kt
│   ├── Screen.kt
│   └── SetupNavigation.kt
├── ui
│   ├── component
│   │   ├── beneficiary
│   │   ├── dashboard
│   │   ├── donation
│   │   ├── form
│   │   ├── history
│   │   ├── profile
│   │   └── shared
│   ├── screen
│   │   ├── auth
│   │   ├── beneficiary
│   │   ├── donation
│   │   ├── history
│   │   ├── home
│   │   ├── main
│   │   └── profile
│   ├── theme
│   └── utils
└── viewmodel
    ├── auth
    ├── beneficiary
    ├── bible
    ├── donation
    ├── history
    ├── home
    └── profile
```

## Organização da Arquitetura

O projeto segue uma arquitetura em camadas baseada em MVVM e Repository Pattern.

| Diretório | Responsabilidade |
|-----------|------------------|
| `data` | Persistência local (Room), acesso remoto (Firebase e API), mappers e implementações dos repositórios. |
| `domain` | Modelos de domínio e contratos (interfaces) dos repositórios. |
| `ui` | Componentes reutilizáveis, telas e tema da aplicação utilizando Jetpack Compose. |
| `viewmodel` | Gerenciamento de estado e regras de apresentação das telas. |
| `navigation` | Definição das rotas e fluxo de navegação. |
| `di` | Configuração da injeção de dependência com Koin. |
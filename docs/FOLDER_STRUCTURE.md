## Estrutura da pasta 

```bash
com.example.givchurch
├── domain                         NOVA: Camada de Negócio (Pura)
│   ├── model                      <- MODELOS ATUAIS (Movidos para cá)
│   │   ├── Beneficiary.kt
│   │   ├── Donation.kt
│   │   ├── User.kt
│   │   └── enums
│   │       ├── DonationCategory.kt
│   │       └── DonationStatus.kt
│   └── repository                 <- REPOSITÓRIOS ATUAIS (Virando Interfaces)
│       ├── AuthRepository.kt
│       ├── BeneficiaryRepository.kt
│       ├── DashboardRepository.kt
│       ├── DonationRepository.kt
│       └── enums
│           └── SortDirection.kt
│
├── data                           Camada de Dados (Infraestrutura)
│   ├── local                      <- NOVA: Configuração do Banco (Room)
│   │   ├── AppDatabase.kt
│   │   └── dao
│   │       ├── BeneficiaryDao.kt
│   │       └── DonationDao.kt
│   ├── model                      <- NOVA: Seus novos modelos de Banco/API
│   │   ├── BeneficiaryEntity.kt   
│   │   ├── DonationEntity.kt      
│   │   └── UserEntity.kt          
│   ├── repository                 <- NOVA: Implementações dos Repositórios
│   │   ├── AuthRepositoryImpl.kt
│   │   ├── BeneficiaryRepositoryImpl.kt
│   │   ├── DashboardRepositoryImpl.kt
│   │   └── DonationRepositoryImpl.kt
│   └── mock                       <- Seus mocks atuais (para testes ou temporários)
│       ├── BeneficiaryMockData.kt
│       ├── DonationMockData.kt
│       └── UserMockData.kt
│
├── navigation
│   └── ... (Igual ao seu)
├── ui
│   └── ... (Igual ao seu)
└── viewmodel
    └── ... (Igual ao seu)

```
# Entrega 2

## Descrição

A segunda entrega representa a evolução do protótipo desenvolvido na primeira etapa para uma aplicação funcional. Nesta versão foram implementadas as regras de negócio, persistência de dados, autenticação de usuários, arquitetura em camadas e testes automatizados.

O projeto passou a utilizar **Room Database** para armazenamento local, **Firebase Authentication** para autenticação, **Koin** para injeção de dependência e **Jetpack Compose** para construção da interface, seguindo os princípios de **MVVM**, **Repository Pattern** e **Clean Architecture**.

---

# Funcionalidades Implementadas

## Autenticação

- Cadastro de usuários
- Login
- Recuperação de senha
- Logout
- Gerenciamento do perfil do usuário
- Upload e atualização da foto de perfil
- Exibição de versículos bíblicos aleatórios na tela de login
- Controle reativo da sessão do usuário utilizando Firebase Authentication

---

## Dashboard

- Exibição de métricas gerais das doações
- Quantidade de doações pendentes e entregues
- Estatísticas agrupadas por período
- Saudação personalizada utilizando os dados do usuário autenticado

---

## Gestão de Beneficiários

- Cadastro de beneficiários
- Atualização dos dados
- Exclusão de beneficiários
- Registro de observações
- Associação entre beneficiários e doações
- Isolamento dos dados por usuário autenticado

---

## Gestão de Doações

- Cadastro de doações
- Atualização de doações
- Exclusão de doações
- Visualização detalhada
- Histórico de doações
- Upload de imagens pela galeria
- Persistência das imagens localmente
- Controle de categoria
- Controle de status
- Controle de prazo utilizando `LocalDateTime`
- Associação entre doações e beneficiários
- Pesquisa com debounce
- Filtros por categoria

---

## Perfil

- Atualização dos dados do usuário
- Alteração da foto de perfil
- Alternância entre tema claro e escuro
- Exibição das informações reais do usuário autenticado

---

# Arquitetura

Durante esta entrega a arquitetura da aplicação foi completamente estruturada utilizando:

- MVVM (Model-View-ViewModel)
- Repository Pattern
- Clean Architecture
- Dependency Injection com Koin
- Room Database
- Firebase Authentication
- Componentes reutilizáveis em Jetpack Compose
- Navegação utilizando Navigation Compose
- Mapeamento entre modelos de domínio e persistência
- Separação entre camadas de Data, Domain e Presentation

Mais detalhes podem ser encontrados em:

- [ARCHITECTURE.md](./ARCHITECTURE.md)

---

# Persistência de Dados

A aplicação passou a utilizar:

## Local

- Room Database
- DAOs
- TypeConverters
- Entidades
- Mappers

## Remoto

- Firebase Authentication
- Firebase User Service
- API de versículos bíblicos utilizando Ktor Client

---

# Estrutura do Projeto

Durante esta entrega o projeto foi reorganizado em módulos e camadas, facilitando a manutenção e evolução da aplicação.

A estrutura completa encontra-se em:

- [FOLDER_STRUCTURE.md](./FOLDER_STRUCTURE.md)

---

# Diagramas

A documentação técnica produzida nesta entrega inclui:

- [ARCHITECTURE.md](./ARCHITECTURE.md)
- [CLASS_DIAGRAM.md](./CLASS_DIAGRAM.md)

---

# Qualidade de Software

Foram implementados testes unitários para as principais camadas da aplicação.

## ViewModels

- Login
- Registro
- Beneficiários
- Doações
- Dashboard
- Perfil
- Versículos

## Repositories

- Authentication
- Beneficiários
- Doações
- Dashboard
- Usuários
- API de Versículos

## Mappers

- BeneficiaryMapper
- DonationMapper
- UserMapper
- VerseMapper

---

# Principais Evoluções em Relação à Primeira Entrega

- Implementação completa da arquitetura MVVM
- Separação em camadas seguindo Clean Architecture
- Persistência local utilizando Room
- Autenticação utilizando Firebase
- Navegação completa da aplicação
- Dashboard funcional
- CRUD completo de beneficiários
- CRUD completo de doações
- Histórico de doações
- Perfil do usuário
- Upload de imagens
- Tema claro e escuro
- Consumo de API externa
- Injeção de dependência com Koin
- Testes unitários
- Documentação arquitetural
- Diagramas técnicos
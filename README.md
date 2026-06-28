[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/AR7CADm8)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23526831)

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/AR7CADm8)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23526831)

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/AR7CADm8)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23526831)

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/AR7CADm8)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23526831)

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/AR7CADm8)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23526831)

# Gestão de Doações

Aplicativo mobile desenvolvido para auxiliar igrejas e organizações sociais no gerenciamento de doações e beneficiários. A aplicação permite cadastrar, acompanhar e registrar entregas de itens, oferecendo uma interface moderna, autenticação de usuários e uma arquitetura escalável baseada em boas práticas de desenvolvimento Android.

## Membros da Equipe

* **Matrícula:** 555497
* **Nome:** Rubens Rabêlo Soares
* **Curso:** Sistemas de Informação

## Objetivo Geral

Desenvolver um aplicativo Android para gerenciar o ciclo completo de doações, desde o cadastro dos beneficiários e dos itens até o controle das entregas, fornecendo uma solução organizada, segura e intuitiva para instituições que realizam ações sociais.

## Público-Alvo

O sistema é destinado a igrejas, instituições filantrópicas e organizações sem fins lucrativos que necessitam administrar doações e acompanhar sua distribuição entre beneficiários de forma organizada.

## Impacto Esperado

O aplicativo busca facilitar a gestão de ações sociais por meio da digitalização dos processos de cadastro e acompanhamento das doações. Espera-se reduzir erros de controle, melhorar a organização das informações e aumentar a transparência durante a distribuição dos recursos.

## Principais Funcionalidades

### Autenticação

* Cadastro e login de usuários
* Recuperação de senha
* Logout
* Gerenciamento de perfil
* Foto de perfil
* Exibição de versículos bíblicos aleatórios na tela de login

### Gestão de Doações

* Cadastro de doações
* Edição e exclusão de doações
* Cadastro de imagens pela galeria
* Visualização detalhada da doação
* Histórico de doações
* Filtros por categoria
* Controle de prazo utilizando `LocalDateTime`

### Gestão de Beneficiários

* Cadastro de beneficiários
* Edição e exclusão
* Registro de observações
* Associação entre beneficiários e doações

### Dashboard

* Visualização de métricas das doações
* Estatísticas gerais da aplicação

### Perfil

* Atualização dos dados do usuário
* Alteração da foto de perfil
* Alternância entre tema claro e escuro

### Arquitetura

* Arquitetura em camadas
* MVVM
* Repository Pattern
* Clean Architecture
* Persistência local utilizando Room
* Integração com Firebase Authentication
* Injeção de dependência com Koin
* Componentes reutilizáveis em Jetpack Compose
* Mapeamento entre modelos de domínio e persistência
* Gerenciamento reativo da sessão do usuário

### Qualidade

* Testes unitários para ViewModels
* Testes unitários para Repositories
* Testes unitários para Mappers

---

## Tecnologias Utilizadas

### Linguagem

* Kotlin

### Interface

* Jetpack Compose
* Material Design 3

### Arquitetura

* MVVM
* Repository Pattern
* Clean Architecture

### Persistência

* Room Database

### Backend

* Firebase Authentication

### Injeção de Dependência

* Koin

### Testes

* JUnit
* MockK
* Coroutines Test

---

## Documentação

Além deste README, o projeto possui documentação complementar organizada na pasta `docs/`, reunindo as entregas da disciplina, diagramas e documentação técnica.

### Entregas

| Documento                                 | Descrição                                                                                                                                                                      |
| ----------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **[DELIVERY_1.md](./docs/DELIVERY_1.md)** | Documentação da primeira entrega, contendo as telas implementadas utilizando dados estáticos (mockados).                                                                       |
| **[DELIVERY_2.md](./docs/DELIVERY_2.md)** | Documentação da segunda entrega, apresentando as funcionalidades implementadas, arquitetura, persistência de dados, testes automatizados e as principais evoluções do projeto. |

### Documentação Técnica

| Documento                                             | Descrição                                                              |
| ----------------------------------------------------- | ---------------------------------------------------------------------- |
| **[ARCHITECTURE.md](./docs/ARCHITECTURE.md)**         | Arquitetura da aplicação, descrição das camadas e diagrama em Mermaid. |
| **[CLASS_DIAGRAM.md](./docs/CLASS_DIAGRAM.md)**       | Diagrama de classes do domínio da aplicação.                           |
| **[FOLDER_STRUCTURE.md](./docs/FOLDER_STRUCTURE.md)** | Organização das pastas e arquivos do projeto.                          |

Essa documentação complementa este README e apresenta em maior nível de detalhe a evolução do projeto, sua arquitetura e organização interna.

---

## Configuração do Firebase

A autenticação dos usuários é realizada utilizando **Firebase Authentication**.

Por motivos de segurança, o arquivo `google-services.json` **não é versionado** neste repositório. Portanto, antes de executar a aplicação, é necessário criar um projeto no Firebase, registrar a aplicação Android e obter esse arquivo de configuração.

Após realizar o download, coloque o arquivo no seguinte diretório:

```text
app/google-services.json
```

Estrutura esperada:

```text
GivChurch/
├── app/
│   ├── google-services.json
│   ├── src/
│   └── build.gradle.kts
└── ...
```

> **Importante:** sem esse arquivo o Firebase não será inicializado e funcionalidades como cadastro, login, recuperação de senha, gerenciamento do perfil e sincronização dos dados do usuário não estarão disponíveis.

---

## Instruções para Execução

```bash
# Clone o repositório
git clone <URL_DO_REPOSITÓRIO>

# Entre no diretório do projeto
cd <NOME_DO_REPOSITÓRIO>

# Configure o Firebase
# Copie o arquivo google-services.json para:
# app/google-services.json

# Abra o projeto no Android Studio

# Aguarde a sincronização do Gradle

# Execute em um dispositivo físico ou emulador Android
```

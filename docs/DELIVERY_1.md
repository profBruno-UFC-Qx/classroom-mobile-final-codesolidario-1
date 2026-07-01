# Entrega 1

## Descrição

A primeira entrega consiste na implementação das telas propostas utilizando **dados estáticos (mockados/fixos)**, sem integração com backend ou banco de dados neste estágio do projeto.

## Telas Desenvolvidas

As telas implementadas estão organizadas no diretório `ui/screen/`, separadas por módulos para facilitar a manutenção e a organização do projeto. Todas as telas possuem suporte a **Preview** para visualização direta no Android Studio.

### Módulo `auth/`

* [Tela de Login](./GivChurch/app/src/main/java/com/example/givchurch/ui/screen/auth/LoginScreen.kt)
* [Tela de Registro](./GivChurch/app/src/main/java/com/example/givchurch/ui/screen/auth/RegisterScreen.kt)

### Módulo `home/`

* [Tela Inicial (Home)](./GivChurch/app/src/main/java/com/example/givchurch/ui/screen/home/MainHomeScreen.kt)

### Módulo `beneficiary/`

* [Tela Principal de Beneficiários](./GivChurch/app/src/main/java/com/example/givchurch/ui/screen/beneficiary/MainBeneficiaryScreen.kt)
* [Tela de Cadastro de Beneficiário](./GivChurch/app/src/main/java/com/example/givchurch/ui/screen/beneficiary/AddBeneficiaryScreen.kt)

### Módulo `donation/`

* [Tela Principal de Doações](./GivChurch/app/src/main/java/com/example/givchurch/ui/screen/donation/MainDonationScreen.kt)
* [Tela de Cadastro de Doação](./GivChurch/app/src/main/java/com/example/givchurch/ui/screen/donation/AddDonationScreen.kt)

### Módulo `history/`

* [Tela de Histórico de Doações](./GivChurch/app/src/main/java/com/example/givchurch/ui/screen/history/MainHistoryScreen.kt)

### Módulo `profile/`

* [Tela de Perfil](./GivChurch/app/src/main/java/com/example/givchurch/ui/screen/profile/MainProfileScreen.kt)

## Componentes Compartilhados

Além das telas específicas, o projeto conta com um componente reutilizável compartilhado entre as interfaces: a **barra de navegação inferior (Bottom Navigation Bar)**, localizada em:

`main/MainAppContainer`

Esse componente é responsável pela navegação principal da aplicação e é utilizado como estrutura base nas telas do sistema.

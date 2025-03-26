# Restaurant Reservation API

Este projeto é uma API RESTful para gerenciamento de reservas em restaurantes, permitindo o cadastro de usuários, criação e administração de reservas, além de integração com mecanismos de autenticação e performance.

## Índice

- [Introdução](#introdução)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação)
- [Uso](#uso)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Testes](#testes)
- [Contribuição](#contribuição)
- [Licença](#licença)

## Introdução

A **Restaurant Reservation API** oferece uma solução robusta para o gerenciamento de reservas em restaurantes, com suporte a múltiplos usuários, horários disponíveis e lógica de controle de conflitos. O projeto segue princípios modernos de desenvolvimento e práticas como microsserviços, testes automatizados e performance com Gatling.

## Funcionalidades

- Cadastro e gerenciamento de usuários.
- Criação, atualização e cancelamento de reservas.
- Validação de horários e disponibilidade.
- Testes automatizados de integração e performance (Gatling).
- Auditoria de entidades com timestamps.
- Suporte a deploy com Docker.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Web**
- **PostgreSQL**
- **Docker e Docker Compose**
- **Gradle**
- **JUnit 5 & Cucumber** – para testes de integração
- **Gatling** – para testes de performance

## Instalação

1. Clone o repositório:
   ```bash
   git clone https://github.com/thiagomag/restaurant-reservation-api.git
   cd restaurant-reservation-api

2. Configure o banco de dados (PostgreSQL) e variáveis de ambiente, se necessário. Exemplo com Docker:

```bash
  docker-compose up -d
```

3. Compile e rode a aplicação localmente:

```bash
  ./gradlew bootRun
```

##Uso

A API estará disponível por padrão em http://localhost:8080.

Você pode testar os endpoints com ferramentas como Postman, Insomnia ou Swagger (se configurado).

Estrutura do Projeto

- **controller** – camadas REST da aplicação.
- **service** – lógica de negócio.
- **repository** – camada de persistência usando Spring Data R2DBC.
- **domain** – entidades de domínio.
- **config** – configurações de segurança, banco, auditoria.
- **test** – testes com JUnit, Cucumber e Gatling.

##Testes

###Executar testes unitários e de integração:
```bash
./gradlew test
```

###Executar testes de performance (Gatling):
```bash
./gradlew performanceTest
```

###Os relatórios de performance podem ser encontrados em:

```bash
build/reports/gatling
```

## Contribuição
Contribuições são bem-vindas! Para colaborar:

## Fork este repositório.

Crie uma branch com sua feature (git checkout -b feature/NovaFuncionalidade).

Commit suas mudanças (git commit -m 'Adiciona nova funcionalidade').

Push para a sua branch (git push origin feature/NovaFuncionalidade).

Abra um Pull Request.

## Licença
Este projeto está licenciado sob a Licença MIT. Consulte o arquivo LICENSE para mais detalhes.

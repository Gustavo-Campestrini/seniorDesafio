# Desafio Hotel Senior Sistemas

## Autor
**Gustavo Henrique Campestrini**  
[LinkedIn](https://www.linkedin.com/in/gustavo-campestrini/)

## Descrição
Este projeto é uma aplicação que possibilita o cadastro de hóspedes e a realização de check-in em um hotel.

## Tecnologias Utilizadas
- **Back-end:** Java, Spring Boot, PostgreSQL
- **Front-end:** Angular, Bootstrap

## Requisitos do Sistema
- Java JDK 17.
- Node.js e npm (versão mais recente recomendada).
- PostgreSQL.

## Configuração do Banco de Dados
1. Crie um banco de dados PostgreSQL chamado `hotel`.
2. Atualize o arquivo `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/hotel
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha

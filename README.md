# Recrutamento-Qmagico

Projeto de um fórum com Spring boot e Angular. https://recrutamento-qmagico.herokuapp.com

## 1 Build

### 1.1 Pré-requisitos

- [JDK 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
- [Git](https://git-scm.com/)
- [Maven](https://maven.apache.org/)
- [MySQL](https://www.mysql.com/)

### 1.2 Build

1. Faça clone do repositório com:
**git clone https://gitlab.com/geovannyAvelar/Recrutamento-Qmagico**

2. Entre no diretório:
**cd Recrutamento-Qmagico**

3. Entre no MySQL.

4. Crie uma base de dados chamada recrutamento:
**create database recrutamento;**

5. Rode o SQL para a geração das tabelas:
**source recrutamento-ddl.sql**

6. Rode o projeto com:
**mvn spring-boot:run**

Foi criado um usuário com username root e senha root. Para acessar a aplicação entre 
em http://localhost:8080.


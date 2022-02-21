# Budget Control

Aplicação para controle financeiro pessoal ou familiar, faz o controle de despesas e despesas.

Feito com base no [Alura Challenge Back-End 2](https://www.alura.com.br/challenges/back-end-2/)

## 🚀 Como executar

### 📋 Pré-requisitos

* JDK 17 ou superior

### 🔧 Instalação

Para baixar as dependências, compilar e executar os testes execute:

```
./mvnw clean install
```

Para executar execute:

```
./mvnw spring-boot:run
```

## ⌨ Uso

O projeto será executado em http://localhost:8080/ e será possível enviar requisições a API.
Para acessar a documentação entre em /swagger-ui.html
Collection do Postman disponível em: [Massa de testes](Budget%20Control.postman_collection.json)

### 🔑🔑🔒 Segurança

Os recursos da aplicação são protegidos. Para acessar é necessário cadastrar um usuário em /usuarios/cadastrar, 
enviando um json contendo o username e a senha: {"username":"example","password":""example}.

O login é feito em /login, enviando o username e a senha em form-data. 
O token recebido é do tipo bearer, e deve ser inserido no header das requisições.

### 💾💾💾💾💿 Banco de dados

A aplicação usa o banco de dados em memória H2, para acessar seu console entre em /h2-console


## 🛠️ Construído com

Criado utilizando as tecnologias:

* [Java 17](https://jdk.java.net/17/) - Linguagem
* [Maven](https://maven.apache.org/) - Gerenciamento de Dependências
* [Spring-boot 2.6.3](https://spring.io/projects/spring-boot/) - Framework web
* [Junit 5](https://junit.org/junit5/) - Testes unitários
* [H2 Embedded database](https://www.h2database.com/html/main.html) - Banco de dados

## ✒️ Autor

* [Caio Lima](https://github.com/Caio042)
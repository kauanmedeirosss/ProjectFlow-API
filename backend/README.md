# ProjectFlow API

## 📋 Índice
- [Configuração no Postman](#-configuração-no-postman)
- [Configuração do Banco de Dados](#-configuração-do-banco-de-dados)
- [Autenticação e Uso da API](#-autenticação-e-uso-da-api)
- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias e Arquitetura](#-tecnologias-e-arquitetura)
- [Módulos da API](#-módulos-da-api)

---

## 🚀 Configuração no Postman

### Importar Coleções
1. Abra o Postman.
2. Clique em `Import`.
3. Selecione os arquivos `ProjectFlow-API.postman_collection.json` e `ProjectFlow-API-Enviroment.json` do projeto.
    * Esses arquivos estão presentes na pasta `postman`, presente na raiz do projeto.
4. Clique em `Import` para adicionar todas as requisições.

---

## 🗄️ Configuração do Banco de Dados

### 1. Configurar Conexão
Edite o arquivo `application.yml` com suas credenciais:

```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/projectflow_api
    username: seu_usuario
    password: 'sua_senha'
```

### 2. Executar Scripts de Inserção
Localize o arquivo `anotacoes/sql/comandos_postgresql.txt`, presente na raíz do projeto, que contém:
* Criação e seleção do schema
* Criação de tabelas
* Inserção de dados para teste  

Execute o script no seu banco PostgreSQL para popular os dados iniciais.

---

## 🔐 Autenticação e Uso da API

### 1. Cadastrar Usuário
Na coleção do Postman, localize a requisição `POST /usuarios`.
```json
{
  "nome": "administrador teste",
  "email": "adminteste@email.com",
  "senha": "admin123",
  "role": "ADMIN"
}
```

### 2. Realizar Login
Na coleção do Postman, localize a requisição `POST /login`.  
Caso não tenha criado um usuário, no body, use as credenciais padrão:
```json
{
  "email": "admintest@email.com",
  "senha": "admin123"
}
```
Execute a requisição e copie o token retornado SEM ASPAS.

### 3. Configurar Token
* No Environment do Postman, cole o token na variável token.
* Ou configure o Auth Type como Bearer Token em cada requisição.

### 4. Acessar Documentação Swagger
[Swagger Ui](http://localhost:8080/swagger-ui/index.html#/)

---

## 📖 Sobre o Projeto
O ProjectFlow API é um sistema completo para gerenciamento de tarefas, equipes e projetos. Desenvolvido para oferecer uma solução robusta e escalável para organização de workflows empresariais.

### Objetivos Principais
* Gerenciamento eficiente de tarefas e projetos
* Relatórios de progressos

---

## 🏗️ Tecnologias e Arquitetura

### Arquitetura
* Monolítica - Aplicação única e coesa
* RESTful API - Padrão arquitetural para APIs web
* MVC (Model-View-Controller) - Separação de concerns

### Segurança
* JWT (JSON Web Tokens) para autenticação
* Spring Security para controle de acesso
* BCrypt para hash de senhas

### Mapeamento e Documentação
* MapStruct - Mapeamento entre entidades e DTOs
* Swagger/OpenAPI - Documentação interativa da API
* Validações Bean Validation - Dados consistentes

### Stack Tecnológica
* Java 21 - Linguagem principal
* Spring Boot 3.5 - Framework backend
* PostgreSQL - Banco de dados relacional
* Maven - Gerenciamento de dependências

---

## 📊 Módulos da API

### 1. Autenticação
* Login e geração de tokens JWT
* Controle de sessões e segurança

### 2. Gerenciamento de Equipes
* CRUD completo de equipes
* Associação de usuários às equipes
* Controle hierárquico de acesso

### 3. Gerenciamento de Tarefas
* Criação, edição e exclusão de tarefas
* Controle de status e prazos

### 4. Gerenciamento de Anexos
* Upload e download de arquivos
* Controle de storage

### 5. Gerenciamento de Comentários
* CRUD completo de comentários

### 6. Gerenciamento de Projetos
* Criação, edição e exclusão de projetos
* Atualização de status

### 7. Gerenciamento de Usuários
* CRUD hierarquico completo de usuários

### 8. Relatórios
* Relatórios de progesso de projetos

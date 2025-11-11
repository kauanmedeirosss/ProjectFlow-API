# ProjectFlow API

## 📋 Índice
- [Execução Rápida](#-execução-rápida)
- [Configuração no Postman](#-configuração-no-postman)
- [Configuração do Banco de Dados](#-configuração-do-banco-de-dados)
- [Autenticação e Uso da API](#-autenticação-e-uso-da-api)
- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias e Arquitetura](#-tecnologias-e-arquitetura)
- [Módulos da API](#-módulos-da-api)

---

## 🚀 Execução Rápida

### Opção 1: Desenvolvimento com H2 (Recomendado para Testes)
````bash
# Executar com banco H2 em memória (não requer configuração)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
````

#### Acesso:
* 🔗 API: http://localhost:8080
* 📚 Swagger UI: http://localhost:8080/swagger-ui/index.html
* 🗄️ H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:projectflow_db`
  - Username: `sa`
  - Password: (vazio)

### Opção 2: Produção com PostgreSQL
````bash
# Executar com PostgreSQL (requer banco configurado)
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
````

## 🚀 Configuração no Postman

### Importar Coleções
1. Abra o Postman.
2. Clique em `Import`.
3. Selecione os arquivos da pasta `postman`:
    * `ProjectFlow-API.postman_collection.json`
    * `ProjectFlow-API-Enviroment.json`
4. Clique em `Import` para adicionar todas as requisições.

---

## 🗄️ Configuração do Banco de Dados

### 1. Configurar Conexão PostgreSQL
Edite o arquivo `application.yml` com suas credenciais:

```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/projectflow_api
    username: seu_usuario
    password: 'sua_senha'
```

### 2. Executar Scripts de Inserção
Localize o arquivo `anotacoes/sql/comandos_postgresql.txt`, na raíz do projeto, que contém:
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
* ✅ Gerenciamento eficiente de tarefas e projetos
* 📈 Relatórios de progressos em tempo real
* 👥 Controle de equipes e colaboradores
* 🔐 Segurança robusta com autenticação JWT

---

## 🏗️ Tecnologias e Arquitetura

### 🏛️ Arquitetura
* Monolítica - Aplicação única e coesa
* RESTful API - Padrão arquitetural para APIs web
* MVC (Model-View-Controller) - Separação de concerns
* Camadas: Controller → Service → Repository → Entity

### Segurança
* JWT (JSON Web Tokens) para autenticação
* Spring Security para controle de acesso
* BCrypt para hash de senhas
* Controle de Roles: ADMIN, GERENTE, MEMBRO

### Mapeamento e Documentação
* MapStruct - Mapeamento entre entidades e DTOs
* Swagger/OpenAPI - Documentação interativa da API
* Bean Validation - Validações de dados consistentes
* Lombok - Redução de boilerplate code

### Stack Tecnológica
* Java 21 - Linguagem principal
* Spring Boot 3.5 - Framework backend
* PostgreSQL - Banco de dados relacional (Produção)
* H2 Database - Banco em memória (Desenvolvimento)
* Maven - Gerenciamento de dependências
* Spring Data JPA - Persistência de dados

---

## 📊 Módulos da API

### 1. Autenticação
* Login e geração de tokens JWT
* Controle de sessões e segurança
* Refresh token automático

### 2. Gerenciamento de Equipes
* CRUD completo de equipes
* Associação de usuários às equipes
* Controle hierárquico de acesso
* Listagem de membros por equipe

### 3. Gerenciamento de Tarefas
* Criação, edição e exclusão de tarefas
* Controle de status e prazos
* Atribuição de cessionários
* Controle de prioridades (BAIXA, MÉDIA, ALTA, CRÍTICA)

### 4. Gerenciamento de Anexos
* Upload e download de arquivos
* Controle de storage
* Associação com tarefas específicas

### 5. Gerenciamento de Comentários
* CRUD completo de comentários
* Associação com tarefas e autores
* Timestamp automático de criação

### 6. Gerenciamento de Projetos
* Criação, edição e exclusão de projetos
* Atualização de status (PLANEJAMENTO, EM_PROGRESSO, COMPLETO, CANCELADO)
* Controle de prazos e orçamentos
* Associação com equipes

### 7. Gerenciamento de Usuários
* CRUD hierarquico completo de usuários
* Controle de roles e permissões
* Validação de email único

### 8. Relatórios
* Relatórios de progesso de projetos
* Métricas e indicadores de performance
* Dados consolidados para tomada de decisão
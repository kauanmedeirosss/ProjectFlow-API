# Postman Collection - ProjectFlow API

Esta pasta contém as collections e environments para testar a API ProjectFlow.

## 📋 Collections Disponíveis

- `ProjectFlow-API.postman_collection.json` - Collection completa com todos os endpoints

## 🌐 Environments

- `ProjectFlow-API-Environment.json` - Para ambiente local (http://localhost:8080)

## 🚀 Como Usar

1. **Importe a collection:**
    - Abra o Postman
    - Clique em "Import"
    - Selecione `ProjectFlow-API.postman_collection.json`

2. **Importe o environment:**
    - Clique no botão de environments (olhinho)
    - "Import" → Selecione o arquivo de environment desejado

3. **Configure as variáveis:**
    - `baseUrl`: URL base da API
    - `token`: Token JWT (será automaticamente atualizado no login)

## 🔐 Autenticação

1. Execute o request "Login" na collection de Autenticação
2. Copie o token retornado
3. Cole na variável `token` do environment
4. Todos os requests subsequentes usarão automaticamente o token

## 📊 Endpoints Incluídos

- ✅ Autenticação (Login)
- ✅ Usuários (CRUD)
- ✅ Equipes (CRUD + gerenciamento de membros)
- ✅ Projetos (CRUD)
- ✅ Tarefas (CRUD)
- ✅ Comentários (CRUD)
- ✅ Anexos (CRUD)
- ✅ Relatórios
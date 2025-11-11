# 📌 Tutorial: Importando Requisições no Postman

Este projeto possui os arquivos necessários para importar as requisições no **Postman**.

## 📂 Arquivos Disponíveis
- **`ProjectFlow_API.postman_collection.json`** → contém todas as requisições da API (login, usuários, equipes, projetos etc.).
- **`ProjectFlow-API-Environment.json`** → define variáveis de ambiente como `baseUrl` e `token`.
- **`import-postman.js`** → script auxiliar para listar os arquivos JSON (opcional, não necessário para o Postman).

---

## 🚀 Passo a Passo para Importar no Postman

1. Abra o **Postman**.
2. Clique no botão **Import** (canto superior esquerdo).
3. Selecione o arquivo:
   - `ProjectFlow_API.postman_collection.json`
4. Repita o processo e importe também o arquivo:
   - `ProjectFlow-API-Environment.json`
5. No canto superior direito do Postman, selecione o ambiente **`ProjectFlow-API-Environment`**.
6. Atualize o valor das variáveis se necessário:
   - **`baseUrl`** → URL da sua API (ex.: `http://localhost:8080`)
   - **`token`** → insira o JWT válido após autenticação

---

## ✅ Conclusão

Após seguir os passos:
- Todas as requisições estarão organizadas no Postman.
- Você poderá executar testes facilmente apenas ajustando o **token** e o **baseUrl** no ambiente.


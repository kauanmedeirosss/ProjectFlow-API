# Relacionamentos
## 1. Usuario N -- N Equipe
### 1.1 Observações
* Usado Set<> em vez de List<> para evitar duplicidades
* O @JoinTable fica no lado proprietário (ex: Equipe)
* O mappedBy fica no lado inverso (ex: Usuario)
* o JPA cria automaticamente a tabela de junção (membros_equipe)

### 1.2 Fluxo
* Criar equipe
* Criar usuários
* Adicionar usuários à equipe via endpoint POST /equipes/{equipeId}/membros/{usuarioId}
* O JPA gerencia automaticamente a tabela de junção 

## 2. Equipe 1 -- N Projeto
### 2.1 Observações
* FetchType.LAZY para melhor performance
* cascade = CascadeType.ALL para operações em cascata
* Validação de nulidade com nullable = false

### 2.2 Fluxo
* Criar equipe
* Criar projeto
* Associar projeto à equipe (setEquipe)
* Ao salvar o projeto herda automaticamente a equipe

## 3. Projeto 1 -- N Tarefa
Nada novo
## 4. Usuario 1 -- N Tarefa (como cessionario)
Nada novo
## 5. Tarefa 1 -- N Comentario
Nada novo
## 6. Tarefa 1 -- N Anexo
Nada novo
## 7. Usuario 1 -- N Comentario (como autor)
Nada novo

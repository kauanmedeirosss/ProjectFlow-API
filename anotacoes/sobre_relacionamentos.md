# Relacionamentos

## 1. Usuario N -- N Equipe
### 1.1 Observações
* Usado Set<> em vez de List<> para evitar duplicidades
* O @JoinTable fica no lado proprietário (ex: Equipe)
* O mappedBy fica no lado inverso (ex: Usuario)
* o JPA cria automaticamente a tabela de junção (membros_equipe)

### 1.2 Fluxo:
* Criar equipe
* Criar usuários
* Adicionar usuários à equipe via endpoint POST /equipes/{equipeId}/membros/{usuarioId}
* O JPA gerencia automaticamente a tabela de junção 
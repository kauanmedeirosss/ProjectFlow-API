export interface Usuario {
  id: number;
  nome: string;
  email: string;
  role: 'ADMIN' | 'GERENTE' | 'MEMBRO';
}

export interface Projeto {
  id: number;
  nome: string;
  descricao: string;
  status: 'PLANEJAMENTO' | 'EM_PROGRESSO' | 'COMPLETO' | 'CANCELADO';
}

export interface Tarefa {
  id: number;
  titulo: string;
  status: 'A_FAZER' | 'EM_PROGRESSO' | 'REVISAO' | 'FEITA';
  cessionario: Usuario;
}

export interface Equipe {
  id: number;
  nome: string;
}

export interface PaginaResponse<T> {
  conteudo: T[];
  paginaAtual: number;
  tamanhoPagina: number;
  totalElementos: number;
  totalPaginas: number;
  primeiraPagina: boolean;
  ultimaPagina: boolean;
}

export interface LoginRequest {
  login: string;
  senha: string;
}

export interface AuthResponse {
  tokenJWT: string;
  role: string;
}
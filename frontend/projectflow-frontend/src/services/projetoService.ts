import { api } from './api';
import { Projeto, PaginaResponse } from '../types/api';

export const projetoService = {
  async listarProjetos(pagina = 0, tamanho = 10): Promise<PaginaResponse<Projeto>> {
    const response = await api.get(`/projetos?pagina=${pagina}&tamanho=${tamanho}&ordenarPor=nome`);
    return response.data;
  },

  async buscarProjeto(id: number): Promise<Projeto> {
    const response = await api.get(`/projetos/${id}`);
    return response.data;
  },

  async criarProjeto(projeto: Omit<Projeto, 'id'>): Promise<Projeto> {
    const response = await api.post('/projetos', projeto);
    return response.data;
  },

  async atualizarProjeto(projeto: Projeto): Promise<Projeto> {
    const response = await api.put('/projetos', projeto);
    return response.data;
  },

  async deletarProjeto(id: number): Promise<void> {
    await api.delete(`/projetos/${id}`);
  }
};
import { api } from './api';
import { LoginRequest, AuthResponse, Usuario } from '../types/api';

export const authService = {
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/login', credentials);
    return response.data;
  },

  async cadastrarUsuario(usuario: Omit<Usuario, 'id'> & { senha: string }) {
    const response = await api.post('/usuarios', usuario);
    return response.data;
  },

  logout() {
    localStorage.removeItem('@ProjectFlow:token');
    localStorage.removeItem('@ProjectFlow:user');
  }
};
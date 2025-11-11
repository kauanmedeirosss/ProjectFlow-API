import React, { createContext, useContext, useState, useEffect } from 'react';
import { authService } from '../services/authService';
import { Usuario } from '../types/api';

interface AuthContextData {
  user: Usuario | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  loading: boolean;
}

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<Usuario | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('@ProjectFlow:token');
    const storedUser = localStorage.getItem('@ProjectFlow:user');

    if (token && storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const login = async (email: string, password: string) => {
    try {
      const response = await authService.login({ login: email, senha: password });
      
      localStorage.setItem('@ProjectFlow:token', response.tokenJWT);
      // Aqui você pode buscar os dados completos do usuário
      const userData: Usuario = {
        id: 1, // Você precisaria buscar isso da API
        nome: 'Usuário',
        email: email,
        role: response.role as any
      };
      
      setUser(userData);
      localStorage.setItem('@ProjectFlow:user', JSON.stringify(userData));
    } catch (error) {
      throw new Error('Credenciais inválidas');
    }
  };

  const logout = () => {
    authService.logout();
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ 
      user, 
      isAuthenticated: !!user, 
      login, 
      logout, 
      loading 
    }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
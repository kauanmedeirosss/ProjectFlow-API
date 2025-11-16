import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);     // dados reais do usuário
  const [token, setToken] = useState(null);
  const [role, setRole] = useState(null);
  const [loading, setLoading] = useState(true);

  // Função para buscar o usuário na API
  async function fetchUserData(jwt) {
    try {
      const response = await fetch("http://localhost:8080/usuarios/me", {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (!response.ok) {
        throw new Error("Falha ao carregar usuário");
      }

      const data = await response.json();
      setUser(data);
    } catch (err) {
      console.error("Erro ao buscar usuário:", err);
      setUser(null);
    }
  }

  // Carrega os dados quando recarrega a página
  useEffect(() => {
    const storedToken = localStorage.getItem("tokenJWT");
    const storedRole = localStorage.getItem("role");

    if (storedToken && storedRole) {
      setToken(storedToken);
      setRole(storedRole);
      fetchUserData(storedToken);
    }

    setLoading(false);
  }, []);

  // Login
  async function login(token, role) {
    localStorage.setItem("tokenJWT", token);
    localStorage.setItem("role", role);

    setToken(token);
    setRole(role);

    await fetchUserData(token); // agora busca o user real
  }

  // Logout
  function logout() {
    localStorage.removeItem("tokenJWT");
    localStorage.removeItem("role");
    setToken(null);
    setRole(null);
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ user, token, role, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}

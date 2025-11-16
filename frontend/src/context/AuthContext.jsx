import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);
  const [role, setRole] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storedToken = localStorage.getItem("tokenJWT");
    const storedRole = localStorage.getItem("role");

    if (storedToken && storedRole) {
      setToken(storedToken);
      setRole(storedRole);
      setUser({}); // opcional: buscar dados do usuário da API
    }
    setLoading(false);
  }, []);

  function login(token, role) {
    localStorage.setItem("tokenJWT", token);
    localStorage.setItem("role", role);
    setToken(token);
    setRole(role);
    setUser({});
  }

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

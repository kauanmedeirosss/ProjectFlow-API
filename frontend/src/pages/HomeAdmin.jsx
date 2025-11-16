import { useAuth } from "../context/AuthContext";
import "./home.css"; // estilos abaixo

export default function HomeAdministrador() {
  const { user } = useAuth();

  if (!user) return <p className="loading">Carregando...</p>;

  return (
    <div className="home-container">
      <h1 className="home-title">
        Bem-vindo, <span>{user.nome}</span> 👑
      </h1>

      <p className="home-subtitle">
        Aqui estão algumas informações importantes da sua conta.
      </p>

      <div className="cards-grid">
        <div className="card">
          <h2>Email</h2>
          <p>{user.email}</p>
        </div>

        <div className="card">
          <h2>Função</h2>
          <p>{user.role}</p>
        </div>

        <div className="card">
          <h2>Status</h2>
          <p>Administrador do sistema</p>
        </div>

        <div className="card highlight">
          <h2>Acesso Especial</h2>
          <p>Gerenciamento global de usuários</p>
        </div>
      </div>
    </div>
  );
}

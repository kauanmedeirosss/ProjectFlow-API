import { useAuth } from "../context/AuthContext";
import "./home.css";

export default function HomeUsuario() {
  const { user } = useAuth();

  if (!user) return <p className="loading">Carregando...</p>;

  return (
    <div className="home-container">
      <h1 className="home-title">
        Olá, <span>{user.nome}</span> 👋
      </h1>

      <p className="home-subtitle">
        Aqui estão os seus dados e informações gerais da conta.
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
          <p>Usuário padrão</p>
        </div>

        <div className="card highlight">
          <h2>Bem-vindo ao sistema</h2>
          <p>Explore as funcionalidades disponíveis para você.</p>
        </div>
      </div>
    </div>
  );
}

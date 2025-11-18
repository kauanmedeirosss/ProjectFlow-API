import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import ButtonLogout from "../components/ButtonLogout";
import "./Home.css";

export default function HomeUsuario() {
  const { user } = useAuth();
  const [sidebarOpen, setSidebarOpen] = useState(false);

  if (!user) return <p>Carregando...</p>;

  return (
    <div className="home-wrapper">
      {/* NAVBAR */}
      <div className="navbar">
        <button
          className="hamburger-btn"
          onClick={() => setSidebarOpen(!sidebarOpen)}
        >
          <span className="bar"></span>
          <span className="bar"></span>
          <span className="bar"></span>
        </button>

        <ButtonLogout />
      </div>

      {/* SIDEBAR */}
      <aside className={`sidebar ${sidebarOpen ? "open" : ""}`}>
        <h3 className="sidebar-title">Menu</h3>
        <ul className="sidebar-menu">
          <li>Dashboard</li>
          <li>Meus Projetos</li>
          <li>Minhas Tarefas</li>
          <li>Configurações</li>
        </ul>
      </aside>

      {/* CONTEÚDO */}
      <div className="home-content">
        <h1 className="home-title">
          Olá, <span>{user.nome}</span> 👋
        </h1>

        <p className="home-description">
          Aqui estão seus dados e informações gerais.
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
    </div>
  );
}

import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import ButtonLogout from "../components/ButtonLogout";
import UserProfileCard from "../components/UserProfileCard";
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

        <UserProfileCard
          nome={user.nome}
          email={user.email}
          role={user.role}
        />
      </div>
    </div>
  );
}

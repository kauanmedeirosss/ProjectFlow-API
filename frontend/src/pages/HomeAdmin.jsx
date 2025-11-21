import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import ButtonLogout from "../components/ButtonLogout";
import UserProfileCard from "../components/UserProfileCard";
import "./Home.css";

export default function HomeAdministrador() {
  const { user } = useAuth();
  const navigate = useNavigate();
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
          <li onClick={() => navigate("/home")}>Dashboard</li>
          <li onClick={() => navigate("/home")}>Gerenciar Usuários</li>
          <li onClick={() => navigate("/gerenciar-projetos")}>Gerenciar Projetos</li>
          <li onClick={() => navigate("/home")}>Perfil</li>
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

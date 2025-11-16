import { useState } from "react";
import "./Home.css";
import { useAuth } from "../context/AuthContext";
import ButtonLogout from "../components/ButtonLogout";

export default function HomeAdmin() {
  const [open, setOpen] = useState(false);
  const { logout } = useAuth();

  return (
    <div className="home-wrapper">
      <aside className={`sidebar ${open ? "open" : ""}`}>
        <h2 className="sidebar-title">ProjectFlow</h2>
        <ul className="sidebar-menu">
          <li>Dashboard</li>
          <li>Projetos</li>
          <li>Equipes</li>
          <li>Perfil</li>
        </ul>
      </aside>

      <nav className="navbar">
        <button className="hamburger-btn" onClick={() => setOpen(!open)}>
          <span className="bar"></span>
          <span className="bar"></span>
          <span className="bar"></span>
        </button>

        <ButtonLogout onClick={logout} />
      </nav>

      <main className="home-content">
        <h2 className="home-title">Bem-vindo ao ProjectFlow (Admin/Manager)</h2>
        <p className="home-description">Aqui é onde sua organização de projetos começa.</p>
      </main>
    </div>
  );
}

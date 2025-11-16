import { useState } from "react";
import "./Home.css";

export default function Home() {
  const [open, setOpen] = useState(false);

  return (
    <div className="home-wrapper">

      {/* SIDEBAR */}
      <aside className={`sidebar ${open ? "open" : ""}`}>
        <h2 className="sidebar-title">ProjectFlow</h2>

        <ul className="sidebar-menu">
          <li>Dashboard</li>
          <li>Projetos</li>
          <li>Equipes</li>
          <li>Configurações</li>
        </ul>
      </aside>

      {/* NAVBAR */}
      <nav className="navbar">
        <button className="hamburger-btn" onClick={() => setOpen(!open)}>
          <span className="bar"></span>
          <span className="bar"></span>
          <span className="bar"></span>
        </button>

      </nav>

      {/* CONTEÚDO */}
      <main className="home-content">
        <h2 className="home-title">Bem-vindo ao ProjectFlow</h2>
        <p className="home-description">
          Aqui é onde sua organização de projetos começa.
        </p>
      </main>

    </div>
  );
}

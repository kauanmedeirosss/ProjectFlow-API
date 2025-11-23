import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ButtonLogout from "../components/ButtonLogout";
import api from "../services/api";

import "./Home.css";
import "./Projetos.css";

export default function GerenciarUsuarios() {
  const navigate = useNavigate();
  const { user } = useAuth();

  const [usuarios, setUsuarios] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sidebarOpen, setSidebarOpen] = useState(false);

  useEffect(() => {
    async function fetchUsuarios() {
      try {
        const response = await api.get("/usuarios");
        setUsuarios(response.data.content || []);
      } catch (error) {
        console.error("Erro ao buscar usuários", error);
      } finally {
        setLoading(false);
      }
    }
    fetchUsuarios();
  }, []);

  if (!user) return <p className="loading">Carregando...</p>;

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

          <li onClick={() => navigate("/gerenciar-equipes")}>
            Gerenciar Equipes
          </li>

          <li onClick={() => navigate("/gerenciar-projetos")}>
            Gerenciar Projetos
          </li>

          <li onClick={() => navigate("/home")}>Perfil</li>
        </ul>
      </aside>

      {/* CONTEÚDO */}
      <div className="home-content">
        <h1 className="home-title">Gerenciar Usuários</h1>
        <p className="home-description">
          Aqui você pode visualizar, editar ou excluir qualquer usuário da plataforma.
        </p>

        {loading ? (
          <p className="loading">Carregando usuários...</p>
        ) : usuarios.length === 0 ? (
          <p className="empty-text">
            Nenhum usuário encontrado no sistema.
          </p>
        ) : (
          <div className="projetos-grid">
            {usuarios.map((u) => (
              <div key={u.id} className="projeto-card">
                <h3 className="projeto-title">{u.nome}</h3>

                <p className="projeto-desc">{u.email}</p>

                <span className="status-badge badge-purple">
                  {u.role}
                </span>

                <button
                  className="projeto-btn"
                  onClick={() => navigate(`/usuarios/${u.id}/gerenciar`)}
                >
                  Gerenciar
                </button>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

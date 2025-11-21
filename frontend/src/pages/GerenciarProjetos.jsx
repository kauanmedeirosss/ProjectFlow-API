import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ButtonLogout from "../components/ButtonLogout";
import api from "../services/api";

import "./Home.css";
import "./Projetos.css";

export default function GerenciarProjetos() {
  const navigate = useNavigate();
  const { user } = useAuth();

  const [projetos, setProjetos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sidebarOpen, setSidebarOpen] = useState(false);

  useEffect(() => {
    async function fetchProjetos() {
      try {
        const response = await api.get("/projetos");
        setProjetos(response.data.content || []);
      } catch (error) {
        console.error("Erro ao buscar projetos", error);
      } finally {
        setLoading(false);
      }
    }
    fetchProjetos();
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
          <li onClick={() => navigate("/gerenciar-usuarios")}>
            Gerenciar Usuários
          </li>
          <li onClick={() => navigate("/gerenciar-projetos")}>
            Gerenciar Projetos
          </li>
          <li onClick={() => navigate("/home")}>Perfil</li>
        </ul>
      </aside>

      {/* CONTEÚDO */}
      <div className="home-content">
        <h1 className="home-title">Gerenciar Projetos</h1>
        <p className="home-description">
          Aqui você pode visualizar, editar ou excluir qualquer projeto da
          plataforma.
        </p>

        {loading ? (
          <p className="loading">Carregando projetos...</p>
        ) : projetos.length === 0 ? (
          <p className="empty-text">
            Nenhum projeto encontrado no sistema.
          </p>
        ) : (
          <div className="projetos-grid">
            {projetos.map((proj) => (
              <div key={proj.id} className="projeto-card">
                <h3 className="projeto-title">{proj.nome}</h3>

                <p className="projeto-desc">{proj.descricao}</p>

                <span className="status-badge badge-blue">
                  {proj.status}
                </span>

                <div className="projeto-info">
                  <p>
                    <strong>Deadline:</strong> {proj.deadline || "--"}
                  </p>
                </div>

                <button
                  className="projeto-btn"
                  onClick={() =>
                    navigate(`/projetos/${proj.id}/gerenciar`)
                  }
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

import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import ButtonLogout from "../components/ButtonLogout";
import api from "../services/api";
import "./Home.css";
import "./Projetos.css";

export default function MeusProjetos() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [projetos, setProjetos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function carregarProjetos() {
      try {
        const response = await api.get("/projetos/meus");
        setProjetos(response.data);
      } catch (err) {
        console.error("Erro ao carregar projetos:", err);
      } finally {
        setLoading(false);
      }
    }

    carregarProjetos();
  }, []);

  const statusColors = {
    PLANEJAMENTO: "badge-yellow",
    EM_PROGRESSO: "badge-blue",
    COMPLETO: "badge-green",
    CANCELADO: "badge-red",
  };

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
          <li onClick={() => navigate("/meus-projetos")}>Meus Projetos</li>
          <li onClick={() => navigate("/minhas-tarefas")}>Minhas Tarefas</li>
          <li onClick={() => navigate("/home-membro")}>Perfil</li>
        </ul>
      </aside>

      {/* CONTEÚDO PRINCIPAL */}
      <div className="home-content">

        <h1 className="home-title">Meus Projetos</h1>

        <p className="home-description">
          Aqui estão todos os projetos vinculados a você.
        </p>

        {loading ? (
          <p className="loading">Carregando projetos...</p>
        ) : projetos.length === 0 ? (
          <p className="empty-text">Você ainda não participa de nenhum projeto.</p>
        ) : (
          <div className="projetos-grid">
            {projetos.map((proj) => (
              <div key={proj.id} className="projeto-card">
                <h2 className="projeto-title">{proj.nome}</h2>

                <p className="projeto-desc">{proj.descricao}</p>

                <div className="projeto-info">
                  <p>
                    <strong>Status:</strong> {proj.status}
                  </p>
                </div>

                <div className="projeto-info">
                  <p>
                    <strong>Equipe:</strong> {proj.equipeNome}
                  </p>
                </div>

                <button
                  className="projeto-btn"
                  onClick={() => navigate(`/projetos/${proj.id}/tarefas`)}
                >
                  Ver Detalhes
                </button>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

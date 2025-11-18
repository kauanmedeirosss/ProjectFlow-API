import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ButtonLogout from "../components/ButtonLogout";
import api from "../services/api";
import "./Home.css";
import "./Projetos.css";

export default function ProjetoDetalhes() {
  const { user } = useAuth();
  const { id } = useParams();
  const navigate = useNavigate();

  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [projeto, setProjeto] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function carregarProjeto() {
      try {
        const response = await api.get(`/projetos/${id}`);
        setProjeto(response.data);
      } catch (err) {
        console.error("Erro ao carregar projeto:", err);
      } finally {
        setLoading(false);
      }
    }

    carregarProjeto();
  }, [id]);

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

      {/* CONTEÚDO */}
      <div className="home-content">
        {loading ? (
          <p className="loading">Carregando...</p>
        ) : !projeto ? (
          <p className="empty-text">Projeto não encontrado.</p>
        ) : (
          <div className="projeto-detalhes-container">
            <h1 className="home-title">{projeto.nome}</h1>

            <span className={`status-badge ${statusColors[projeto.status]}`}>
              {projeto.status}
            </span>

            <p className="projeto-detalhes-desc">{projeto.descricao}</p>

            <div className="projeto-info">
              <p>
                <strong>Equipe:</strong> {projeto.equipeNome}
              </p>
              <p>
                <strong>Data de Início:</strong> {projeto.dataInicio}
              </p>
              <p>
                <strong>Deadline:</strong> {projeto.deadline}
              </p>
            </div>

            <div className="projeto-detalhes-btns">
              <button
                className="projeto-btn"
                onClick={() => navigate("/meus-projetos")}
              >
                ← Voltar
              </button>

              <button
                className="projeto-btn"
                onClick={() => navigate(`/projetos/${projeto.id}/tarefas`)}
              >
                Ver Tarefas
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

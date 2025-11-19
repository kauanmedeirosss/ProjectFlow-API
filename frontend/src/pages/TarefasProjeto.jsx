import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ButtonLogout from "../components/ButtonLogout";
import api from "../services/api";
import "./Home.css";
import "./Projetos.css";

export default function TarefasProjeto() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();

  const [tarefas, setTarefas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const statusColors = {
    A_FAZER: "badge-yellow",
    EM_PROGRESSO: "badge-blue",
    REVISAO: "badge-purple",
    FEITA: "badge-green",
  };

  const statusOptions = [
    { value: "A_FAZER", label: "A Fazer" },
    { value: "EM_PROGRESSO", label: "Em Progresso" },
    { value: "REVISAO", label: "Revisão" },
    { value: "FEITA", label: "Feita" },
  ];

  useEffect(() => {
    async function carregarTarefas() {
      try {
        const response = await api.get(`/projetos/${id}/tarefas`);
        setTarefas(response.data || []);
      } catch (error) {
        console.error("Erro ao carregar tarefas:", error);
      } finally {
        setLoading(false);
      }
    }

    carregarTarefas();
  }, [id]);

  async function alterarStatus(tarefaId, novoStatus) {
    try {
      await api.put(`/tarefas/${tarefaId}/status`, { status: novoStatus });
      setTarefas((prev) =>
        prev.map((t) => (t.id === tarefaId ? { ...t, status: novoStatus } : t))
      );
    } catch (err) {
      console.error("Erro ao alterar status:", err);
    }
  }

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

      <div style={{ padding: "10px 20px" }}>
        <button
          onClick={() => navigate("/meus-projetos")}
          style={{
            display: "flex",
            alignItems: "center",
            gap: "8px",
            background: "#21262d",
            border: "1px solid #30363d",
            color: "white",
            padding: "8px 14px",
            borderRadius: "8px",
            cursor: "pointer",
            fontSize: "14px",
            transition: "0.2s",
          }}
          onMouseEnter={(e) => (e.target.style.background = "#30363d")}
          onMouseLeave={(e) => (e.target.style.background = "#21262d")}
        >
          ← Voltar
        </button>
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
        <h1 className="home-title">Tarefas do Projeto</h1>
        {loading ? (
          <p className="loading">Carregando tarefas...</p>
        ) : tarefas.length === 0 ? (
          <p className="empty-text">Nenhuma tarefa encontrada.</p>
        ) : (
          <div className="projetos-grid">
            {tarefas.map((tarefa) => (
              <div key={tarefa.id} className="projeto-card">
                <h3 className="projeto-title">{tarefa.titulo}</h3>

                <p className="projeto-desc">{tarefa.descricao}</p>

                <span className={`status-badge ${statusColors[tarefa.status]}`}>
                  {statusOptions.find((opt) => opt.value === tarefa.status)?.label}
                </span>

                <div className="projeto-info">
                  <p>
                    <strong>Cessionário:</strong>{" "}
                    {tarefa.cessionario ? tarefa.cessionario.nome : "Não atribuído"}
                  </p>
                </div>

                <select
                  value={tarefa.status}
                  onChange={(e) => alterarStatus(tarefa.id, e.target.value)}
                  style={{
                    marginTop: "10px",
                    width: "100%",
                    padding: "6px",
                    borderRadius: "6px",
                    border: "1px solid #30363d",
                    background: "#0d1117",
                    color: "white",
                    cursor: "pointer",
                  }}
                >
                  {statusOptions.map((opt) => (
                    <option key={opt.value} value={opt.value}>
                      {opt.label}
                    </option>
                  ))}
                </select>

                <button
                    className="projeto-btn"
                    onClick={() => navigate(`/tarefas/${tarefa.id}`)}
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

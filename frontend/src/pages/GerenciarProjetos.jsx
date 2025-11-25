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

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    async function fetchProjetos() {
      try {
        setLoading(true);
        const response = await api.get(`/projetos?page=${page}&size=12`);

        setProjetos(response.data.conteudo || []);
        setTotalPages(response.data.totalPaginas || 1);
      } catch (error) {
        console.error("Erro ao buscar projetos", error);
      } finally {
        setLoading(false);
      }
    }
    fetchProjetos();
  }, [page]);

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
          <li onClick={() => navigate("/dashboard")}>Dashboard</li>
          <li onClick={() => navigate("/gerenciar-equipes")}>
            Gerenciar Equipes
          </li>
          <li
            className="active"
            onClick={() => navigate("/gerenciar-projetos")}
          >
            Gerenciar Projetos
          </li>
          <li onClick={() => navigate("/home")}>Perfil</li>
        </ul>
      </aside>

      {/* CONTEÚDO */}
      <div className="home-content">
        <h1 className="home-title">Gerenciar Projetos</h1>
        <p className="home-description">
          Aqui você pode visualizar, adicionar, editar ou excluir qualquer projeto da
          plataforma.
        </p>

        <button
          className="projeto-btn criar-btn"
          onClick={() => navigate("/projetos/criar")}
        >
          + Criar Novo Projeto
        </button>

        {loading ? (
          <p className="loading">Carregando projetos...</p>
        ) : projetos.length === 0 ? (
          <p className="empty-text">Nenhum projeto encontrado no sistema.</p>
        ) : (
          <>
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
                      <strong>Status:</strong> {proj.status || "--"}
                    </p>
                  </div>

                  <button
                    className="projeto-btn"
                    onClick={() =>
                      navigate(`/projetos/${proj.id}`)
                    }
                  >
                    Gerenciar
                  </button>
                </div>
              ))}
            </div>

            {/* PAGINAÇÃO */}
            <div className="pagination">
              <button
                disabled={page === 0}
                onClick={() => setPage((prev) => prev - 1)}
              >
                ◀ Anterior
              </button>

              <span>
                Página {page + 1} de {totalPages}
              </span>

              <button
                disabled={page + 1 >= totalPages}
                onClick={() => setPage((prev) => prev + 1)}
              >
                Próxima ▶
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}

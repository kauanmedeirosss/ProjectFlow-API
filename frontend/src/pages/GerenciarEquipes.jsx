import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ButtonLogout from "../components/ButtonLogout";
import api from "../services/api";

import "./Home.css";
import "./Projetos.css";

export default function GerenciarEquipes() {
    const [equipes, setEquipes] = useState([]);
    const [paginaAtual, setPaginaAtual] = useState(0);
    const [totalPaginas, setTotalPaginas] = useState(0);
    const [loading, setLoading] = useState(true);
    const [sidebarOpen, setSidebarOpen] = useState(false);

    const navigate = useNavigate();
    const { user } = useAuth();

    useEffect(() => {
        carregarEquipes(paginaAtual);
    }, [paginaAtual]);

    function carregarEquipes(page = 0) {
        setLoading(true);
        api.get(`/equipes?page=${page}&size=6`)
            .then(response => {
                setEquipes(response.data.conteudo);
                setTotalPaginas(response.data.totalPaginas);
            })
            .catch(error => {
                console.error("Erro ao carregar equipes:", error);
                alert("Erro ao carregar equipes.");
            })
            .finally(() => setLoading(false));
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

            {/* SIDEBAR */}
            <aside className={`sidebar ${sidebarOpen ? "open" : ""}`}>
                <h3 className="sidebar-title">Menu</h3>

                <ul className="sidebar-menu">
                    <li onClick={() => navigate("/home")}>Dashboard</li>
                    <li className="active" onClick={() => navigate("/gerenciar-equipes")}>
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
                <h1 className="home-title">Gerenciar Equipes</h1>
                <p className="home-description">
                    Aqui você pode visualizar, adicionar, editar ou excluir qualquer equipe da plataforma.
                </p>

                <button
                    className="projeto-btn criar-btn"
                    onClick={() => navigate("/equipes/criar")}
                >
                    + Nova Equipe
                </button>

                {loading ? (
                    <p className="loading">Carregando equipes...</p>
                ) : equipes.length === 0 ? (
                    <p className="empty-text">Nenhuma equipe encontrada no sistema.</p>
                ) : (
                    <>
                        <div className="projetos-grid">
                            {equipes.map(eq => (
                                <div key={eq.id} className="projeto-card">
                                    <h3 className="projeto-title">{eq.nome}</h3>

                                    <p className="projeto-desc">{eq.descricao}</p>

                                    <div className="projeto-info">
                                        <p>
                                            <strong>Membros:</strong> {eq.quantidadeMembros || "0"}
                                        </p>
                                    </div>

                                    <button
                                        className="projeto-btn"
                                        onClick={() => navigate(`/equipes/${eq.id}`)}
                                    >
                                        Gerenciar
                                    </button>
                                </div>
                            ))}
                        </div>

                        {/* PAGINAÇÃO */}
                        <div className="pagination">
                            <button
                                disabled={paginaAtual === 0}
                                onClick={() => setPaginaAtual(paginaAtual - 1)}
                            >
                                ◀ Anterior
                            </button>

                            <span>
                                Página {paginaAtual + 1} de {totalPaginas}
                            </span>

                            <button
                                disabled={paginaAtual + 1 >= totalPaginas}
                                onClick={() => setPaginaAtual(paginaAtual + 1)}
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
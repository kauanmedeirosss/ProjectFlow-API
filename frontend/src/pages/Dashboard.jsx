import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ButtonLogout from "../components/ButtonLogout";
import api from "../services/api";
import {
    PieChart,
    Pie,
    Cell,
    Tooltip,
    Legend,
    ResponsiveContainer
} from "recharts";
import "./Home.css";
import "./Projetos.css";

export default function Dashboard() {
    const [dados, setDados] = useState(null);
    const [loading, setLoading] = useState(true);
    const [sidebarOpen, setSidebarOpen] = useState(false);
    const navigate = useNavigate();
    const { user } = useAuth();

    useEffect(() => {
        api.get("/dashboard/resumo")
            .then(res => {
                setDados(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Erro ao buscar dashboard:", err);
                setLoading(false);
            });
    }, []);

    if (!user) return <p className="loading">Carregando...</p>;

    // Transformar os dados em formato do Recharts
    const tarefasData = dados ? Object.entries(dados.tarefasPorStatus).map(([name, value]) => ({
        name,
        value
    })) : [];

    const projetosData = dados ? Object.entries(dados.projetosPorStatus).map(([name, value]) => ({
        name,
        value
    })) : [];

    // Cores dos gráficos no estilo do projeto
    const COLORS = ["#0090ff", "#2ecc71", "#ff4757", "#ffa502", "#7d8590", "#a55eea"];

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
                    <li className="active" onClick={() => navigate("/dashboard")}>
                        Dashboard
                    </li>
                    <li onClick={() => navigate("/gerenciar-equipes")}>
                        Gerenciar Equipes
                    </li>
                    <li onClick={() => navigate("/gerenciar-projetos")}>
                        Gerenciar Projetos
                    </li>
                    <li onClick={() => navigate("/home")}>Perfil</li>
                </ul>
            </aside>

            {/* CONTEÚDO PRINCIPAL */}
            <div className="home-content">
                <h1 className="home-title">Dashboard Geral</h1>
                <p className="home-description">
                    Visão geral e métricas do ProjectFlow
                </p>

                {loading ? (
                    <p className="loading">Carregando dashboard...</p>
                ) : !dados ? (
                    <p className="empty-text">Erro ao carregar dados do dashboard.</p>
                ) : (
                    <>
                        {/* CARDS DE MÉTRICAS */}
                        <div className="projetos-grid" style={{ gridTemplateColumns: "repeat(4, 1fr)", gap: "20px", marginBottom: "10px",  maxWidth: "1000px"}}>
                            <div className="dashboard-card" style={{ textAlign: "center", padding: "50px" }}>
                                <h3 style={{ color: "#9ab", fontSize: "1rem", marginBottom: "10px" }}>Usuários</h3>
                                <div style={{ fontSize: "2.5rem", fontWeight: "700", background: "linear-gradient(135deg, #ffffff 0%, #0090ff 100%)", WebkitBackgroundClip: "text", WebkitTextFillColor: "transparent" }}>
                                    {dados.totalUsuarios}
                                </div>
                            </div>

                            <div className="dashboard-card" style={{ textAlign: "center", padding: "50px" }}>
                                <h3 style={{ color: "#9ab", fontSize: "1rem", marginBottom: "10px" }}>Projetos</h3>
                                <div style={{ fontSize: "2.5rem", fontWeight: "700", background: "linear-gradient(135deg, #ffffff 0%, #2ecc71 100%)", WebkitBackgroundClip: "text", WebkitTextFillColor: "transparent" }}>
                                    {dados.totalProjetos}
                                </div>
                            </div>

                            <div className="dashboard-card" style={{ textAlign: "center", padding: "50px" }}>
                                <h3 style={{ color: "#9ab", fontSize: "1rem", marginBottom: "10px" }}>Tarefas</h3>
                                <div style={{ fontSize: "2.5rem", fontWeight: "700", background: "linear-gradient(135deg, #ffffff 0%, #ff4757 100%)", WebkitBackgroundClip: "text", WebkitTextFillColor: "transparent" }}>
                                    {dados.totalTarefas}
                                </div>
                            </div>

                            <div className="dashboard-card" style={{ textAlign: "center", padding: "50px" }}>
                                <h3 style={{ color: "#9ab", fontSize: "1rem", marginBottom: "10px" }}>Equipes</h3>
                                <div style={{ fontSize: "2.5rem", fontWeight: "700", background: "linear-gradient(135deg, #ffffff 0%, #ffa502 100%)", WebkitBackgroundClip: "text", WebkitTextFillColor: "transparent" }}>
                                    {dados.totalEquipes}
                                </div>
                            </div>
                        </div>

                        {/* GRÁFICOS */}
                        <div className="projetos-grid" style={{ gridTemplateColumns: "repeat(auto-fit, minmax(500px, 1fr))" }}>
                            {/* GRÁFICO DE TAREFAS */}
                            <div className="projeto-card">
                                <h3 className="projeto-title" style={{ textAlign: "center", marginBottom: "20px" }}>
                                    Tarefas por Status
                                </h3>
                                <ResponsiveContainer width="100%" height={300}>
                                    <PieChart>
                                        <Pie
                                            data={tarefasData}
                                            dataKey="value"
                                            nameKey="name"
                                            outerRadius={90}
                                            label={({ name, percent }) => `${name} (${(percent * 100).toFixed(0)}%)`}
                                            labelLine={false}
                                        >
                                            {tarefasData.map((_, i) => (
                                                <Cell key={i} fill={COLORS[i % COLORS.length]} />
                                            ))}
                                        </Pie>
                                        <Tooltip 
                                            formatter={(value) => [`${value} tarefas`, 'Quantidade']}
                                            contentStyle={{ 
                                                background: 'rgba(22, 27, 34, 0.95)',
                                                border: '1px solid rgba(48, 54, 61, 0.5)',
                                                borderRadius: '8px',
                                                color: 'white'
                                            }}
                                        />
                                        <Legend 
                                            wrapperStyle={{ 
                                                paddingTop: '20px',
                                                fontSize: '12px'
                                            }}
                                        />
                                    </PieChart>
                                </ResponsiveContainer>
                            </div>

                            {/* GRÁFICO DE PROJETOS */}
                            <div className="projeto-card">
                                <h3 className="projeto-title" style={{ textAlign: "center", marginBottom: "20px" }}>
                                    Projetos por Status
                                </h3>
                                <ResponsiveContainer width="100%" height={300}>
                                    <PieChart>
                                        <Pie
                                            data={projetosData}
                                            dataKey="value"
                                            nameKey="name"
                                            outerRadius={90}
                                            label={({ name, percent }) => `${name} (${(percent * 100).toFixed(0)}%)`}
                                            labelLine={false}
                                        >
                                            {projetosData.map((_, i) => (
                                                <Cell key={i} fill={COLORS[i % COLORS.length]} />
                                            ))}
                                        </Pie>
                                        <Tooltip 
                                            formatter={(value) => [`${value} projetos`, 'Quantidade']}
                                            contentStyle={{ 
                                                background: 'rgba(22, 27, 34, 0.95)',
                                                border: '1px solid rgba(48, 54, 61, 0.5)',
                                                borderRadius: '8px',
                                                color: 'white'
                                            }}
                                        />
                                        <Legend 
                                            wrapperStyle={{ 
                                                paddingTop: '20px',
                                                fontSize: '12px'
                                            }}
                                        />
                                    </PieChart>
                                </ResponsiveContainer>
                            </div>
                        </div>

                        {/* CARDS ADICIONAIS (se houver mais dados) */}
                        {dados.projetosRecentes && dados.projetosRecentes.length > 0 && (
                            <div style={{ marginTop: "40px", width: "100%", maxWidth: "1000px" }}>
                                <h3 className="projeto-title" style={{ marginBottom: "20px" }}>
                                    Projetos Recentes
                                </h3>
                                <div className="projetos-grid">
                                    {dados.projetosRecentes.slice(0, 4).map(proj => (
                                        <div key={proj.id} className="projeto-card">
                                            <h4 style={{ color: "#ffffff", marginBottom: "10px" }}>{proj.nome}</h4>
                                            <p style={{ color: "#c9d1d9", fontSize: "0.9rem" }}>
                                                {proj.descricao?.substring(0, 100)}...
                                            </p>
                                            <span className={`status-badge ${proj.status === 'COMPLETO' ? 'badge-green' : proj.status === 'EM_PROGRESSO' ? 'badge-blue' : 'badge-yellow'}`}>
                                                {proj.status}
                                            </span>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        )}
                    </>
                )}
            </div>
        </div>
    );
}
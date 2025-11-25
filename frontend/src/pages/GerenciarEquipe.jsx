import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../services/api";
import "./GerenciarProjeto.css";
import "./Projetos.css";

export default function GerenciarEquipe() {
    const { id } = useParams();
    const [equipe, setEquipe] = useState(null);
    const [membros, setMembros] = useState([]);
    const [editData, setEditData] = useState({});
    const [loading, setLoading] = useState(true);
    const [modoEdicao, setModoEdicao] = useState(false);
    const navigate = useNavigate();
    const [deleting, setDeleting] = useState(false);
    const [modalAberto, setModalAberto] = useState(false);
    const [usuarios, setUsuarios] = useState([]);
    const [loadingUsuarios, setLoadingUsuarios] = useState(false);

    useEffect(() => {
        api.get(`/equipes/${id}`)
            .then(response => {
                setEquipe(response.data);
                setEditData(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Erro ao buscar equipe:", error);
                setLoading(false);
            });

        carregarMembros();
        carregarEquipe();
    }, [id]);

    function carregarMembros() {
        api.get(`/equipes/${id}/membros`)
            .then(res => setMembros(res.data.conteudo || []))
            .catch(err => console.error("Erro ao buscar membros:", err));
    }

    function handleChange(e) {
        const { name, value } = e.target;
        setEditData(prev => ({ ...prev, [name]: value }));
    }

    function iniciarEdicao() {
        setModoEdicao(true);
    }

    function cancelarEdicao() {
        setEditData(equipe); // restaura original
        setModoEdicao(false);
    }

    function salvarAlteracoes() {
        api.put(`/equipes/${equipe.id}`, editData)
            .then(() => {
                alert("Equipe atualizada!");
                setModoEdicao(false);
                // Recarrega os dados atualizados
                api.get(`/equipes/${id}`)
                    .then(response => {
                        setEquipe(response.data);
                        setEditData(response.data);
                    });
            })
            .catch(err => {
                console.error("Erro ao editar equipe:", err);
                if (err.response?.status === 403) {
                    alert("Você não tem permissão para editar esta equipe.");
                } else {
                    alert("Erro ao salvar alterações.");
                }
            });
    }

    function removerMembro(usuarioId) {
        if (!window.confirm("Remover esse membro da equipe?")) return;

        api.delete(`/equipes/${id}/membros/${usuarioId}`)
            .then(() => {
                alert("Membro removido!");
                carregarMembros();
                // Atualiza a contagem de membros
                api.get(`/equipes/${id}`)
                    .then(response => {
                        setEquipe(response.data);
                        setEditData(response.data);
                    });
            })
            .catch(err => {
                console.error("Erro ao remover membro:", err);
                alert("Erro ao remover membro.");
            });
    }

    async function deletarEquipe() {
        if (deleting) return;

        if (!window.confirm("Tem certeza que deseja excluir esta equipe?")) return;

        try {
            setDeleting(true);

            const response = await api.delete(`/equipes/${id}`);

            if (response.status === 200 || response.status === 204) {
                alert("Equipe removida com sucesso!");
                navigate("/gerenciar-equipes", { replace: true });
                return;
            }

            console.warn("Delete retornou status inesperado:", response.status, response.data);
            alert("Equipe excluída (status inesperado). Ver console para detalhes.");
            navigate("/gerenciar-equipes", { replace: true });
        } catch (error) {
            console.error("Erro ao deletar equipe:", error, error.response?.data);
            if (error.response) {
                if (error.response.status === 403) {
                    alert("Você não tem permissão para excluir esta equipe.");
                } else if (error.response.status === 404) {
                    alert("Equipe não encontrada (já removida?).");
                    navigate("/gerenciar-equipes", { replace: true });
                } else {
                    alert("Erro ao excluir a equipe. Ver console para mais detalhes.");
                }
            } else {
                alert("Erro de rede ao tentar excluir a equipe.");
            }
        } finally {
            setDeleting(false);
        }
    }

    function abrirModalUsuarios() {
        setModalAberto(true);
        setLoadingUsuarios(true);

        api.get("/usuarios?page=0&size=50")
            .then(res => {
                setUsuarios(res.data.conteudo || []);
            })
            .catch(err => {
                console.error("Erro ao buscar usuários:", err);
                alert("Erro ao carregar usuários.");
            })
            .finally(() => setLoadingUsuarios(false));
    }

    function carregarEquipe() {
        api.get(`/equipes/${id}`)
            .then(response => {
                setEquipe(response.data);
                setEditData(response.data);
            })
            .catch(error => console.error("Erro ao recarregar equipe:", error));
    }

    function adicionarMembro(usuarioId) {
        api.post(`/equipes/${id}/membros/${usuarioId}`)
            .then(() => {
                alert("Membro adicionado!");
                carregarMembros();
                carregarEquipe(); // para ATUALIZAR quantidade de membros
                setModalAberto(false);
            })
            .catch(err => {
                console.error("Erro ao adicionar membro:", err);
            });
    }


    if (loading) return <div className="gp-container">Carregando...</div>;
    if (!equipe) return <div className="gp-container">Equipe não encontrada.</div>;

    return (
        <div className="gp-container">

            {/* BOTÃO VOLTAR */}
            <button
                onClick={() => navigate("/gerenciar-equipes")}
                className="btn-voltar"
                style={{ marginBottom: "25px" }}
            >
                ⬅ Voltar
            </button>

            <div className="gp-card">
                <h2>{modoEdicao ? "Editar Equipe" : equipe.nome}</h2>

                {/* NOME */}
                <div className="gp-info-group">
                    <div className="gp-info-item">
                        <label className="gp-info-label">Nome da Equipe</label>
                        {modoEdicao ? (
                            <input
                                className="gp-info-value"
                                type="text"
                                name="nome"
                                value={editData.nome}
                                onChange={handleChange}
                            />
                        ) : (
                            <div className="gp-info-value">{equipe.nome}</div>
                        )}
                    </div>
                </div>

                {/* DESCRIÇÃO */}
                <div className="gp-info-group">
                    <div className="gp-info-item">
                        <label className="gp-info-label">Descrição</label>
                        {modoEdicao ? (
                            <textarea
                                className="gp-info-value"
                                name="descricao"
                                value={editData.descricao}
                                onChange={handleChange}
                                rows={4}
                            />
                        ) : (
                            <div className="gp-info-value">{equipe.descricao}</div>
                        )}
                    </div>
                </div>

                {/* INFORMAÇÕES DA EQUIPE */}
                <div className="gp-info-group">
                    <div className="gp-info-item">
                        <label className="gp-info-label">Total de Membros</label>
                        <div className="gp-info-value">{equipe.quantidadeMembros || "0"}</div>
                    </div>
                </div>

                {/* LISTA DE MEMBROS - VERSÃO ATUALIZADA */}
                <div className="gp-info-group">
                    <div className="gp-info-item">
                        <div className="gp-members-list">
                            <label className="gp-info-label">Membros da Equipe</label>
                            <div className="gp-info-value" style={{ minHeight: "auto", padding: "0" }}>
                                {membros.length === 0 ? (
                                    <div style={{ padding: "15px", textAlign: "center", color: "#7d8590" }}>
                                        Nenhum membro nesta equipe
                                    </div>
                                ) : (
                                    <div style={{ maxHeight: "200px", overflowY: "auto" }}>
                                        {membros.map(m => (
                                            <div key={m.id} style={{
                                                display: "flex",
                                                justifyContent: "space-between",
                                                alignItems: "center",
                                                padding: "10px 15px",
                                                borderBottom: "1px solid rgba(48, 54, 61, 0.3)"
                                            }}>
                                                <span style={{ color: "#c9d1d9" }}>
                                                    {m.nome}
                                                </span>
                                                <button
                                                    className="gp-btn"
                                                    style={{
                                                        background: "linear-gradient(135deg, #ff4757 0%, #ff3742 100%)",
                                                        padding: "6px 12px",
                                                        fontSize: "0.8rem",
                                                        minWidth: "auto"
                                                    }}
                                                    onClick={() => removerMembro(m.id)}
                                                >
                                                    Remover
                                                </button>
                                            </div>
                                        ))}
                                    </div>
                                )}
                            </div>
                        </div>

                        {/* NOVO BOTÃO ABAIXO DA LISTA */}
                        <button
                            className="projeto-btn criar-equipe-btn"
                            onClick={abrirModalUsuarios}
                        >
                            + Adicionar Membro
                        </button>
                    </div>
                </div>

                {/* BOTÕES PRINCIPAIS */}
                <div className="gp-actions">
                    {modoEdicao ? (
                        <>
                            <button className="gp-btn gp-save" onClick={salvarAlteracoes}>
                                Salvar
                            </button>
                            <button className="gp-btn gp-delete" onClick={cancelarEdicao}>
                                Cancelar
                            </button>
                        </>
                    ) : (
                        <>
                            <button className="gp-btn gp-edit" onClick={iniciarEdicao}>
                                Editar Equipe
                            </button>
                            <button className="gp-btn gp-delete" onClick={deletarEquipe}>
                                Excluir Equipe
                            </button>
                        </>
                    )}
                </div>
            </div>
            {modalAberto && (
                <div style={{
                    position: "fixed",
                    top: 0,
                    left: 0,
                    width: "100vw",
                    height: "100vh",
                    background: "rgba(0,0,0,0.65)",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    zIndex: 9999
                }}>
                    <div style={{
                        background: "#161b22",
                        padding: "25px",
                        borderRadius: "10px",
                        minWidth: "400px",
                        maxHeight: "60vh",
                        overflowY: "auto",
                        boxShadow: "0 0 25px rgba(0,0,0,0.4)"
                    }}>
                        <h3 style={{ marginBottom: "15px", color: "#c9d1d9" }}>
                            Selecionar Usuário
                        </h3>

                        {loadingUsuarios ? (
                            <div style={{ color: "#aaa" }}>Carregando usuários...</div>
                        ) : (
                            usuarios.map(u => (
                                <div key={u.id} style={{
                                    display: "flex",
                                    justifyContent: "space-between",
                                    alignItems: "center",
                                    padding: "10px",
                                    borderBottom: "1px solid rgba(255,255,255,0.1)"
                                }}>
                                    <span style={{ color: "#c9d1d9" }}>{u.nome}</span>

                                    <button
                                        onClick={() => adicionarMembro(u.id)}
                                        style={{
                                            background: "#2ecc71",
                                            color: "#000",
                                            borderRadius: "5px",
                                            padding: "5px 10px",
                                            cursor: "pointer",
                                            fontWeight: "bold"
                                        }}
                                    >
                                        +
                                    </button>
                                </div>
                            ))
                        )}

                        <button
                            onClick={() => setModalAberto(false)}
                            style={{
                                marginTop: "20px",
                                width: "100%",
                                background: "#444",
                                color: "#fff",
                                padding: "8px",
                                borderRadius: "6px"
                            }}
                        >
                            Fechar
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}
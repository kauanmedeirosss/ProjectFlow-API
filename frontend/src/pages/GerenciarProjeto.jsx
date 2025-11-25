import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../services/api";
import "./GerenciarProjeto.css";

export default function GerenciarProjeto() {
    const { id } = useParams();
    const [projeto, setProjeto] = useState(null);
    const [editData, setEditData] = useState({});
    const [loading, setLoading] = useState(true);
    const [modoEdicao, setModoEdicao] = useState(false);
    const navigate = useNavigate();
    const [deleting, setDeleting] = useState(false);

    useEffect(() => {
        api.get(`/projetos/${id}`)
            .then(response => {
                setProjeto(response.data);
                setEditData(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Erro ao buscar projeto:", error);
                setLoading(false);
            });
    }, [id]);

    function handleChange(e) {
        const { name, value } = e.target;
        setEditData(prev => ({ ...prev, [name]: value }));
    }

    function iniciarEdicao() {
        setModoEdicao(true);
    }

    function cancelarEdicao() {
        setEditData(projeto); // restaura original
        setModoEdicao(false);
    }

    function salvarAlteracoes() {
        const payload = {
            ...editData,
            dataInicio: formatarDataParaDTO(editData.dataInicio),
            deadline: formatarDataParaDTO(editData.deadline),
        };

        api.put(`/projetos/${id}`, payload)
            .then(response => {
                setProjeto(response.data);
                setModoEdicao(false);
            })
            .catch(error => {
                console.error("Erro ao salvar projeto:", error);
            });
    }

    function formatarDataParaDTO(dateStr) {
        if (!dateStr) return null;
        const [yyyy, mm, dd] = dateStr.split("-");
        return `${dd}/${mm}/${yyyy}`;
    }

    async function deletar() {
        // evita múltiplas requests
        if (deleting) return;

        if (!window.confirm("Tem certeza que deseja excluir este projeto?")) return;

        try {
            setDeleting(true);

            const response = await api.delete(`/projetos/${id}`);

            if (response.status === 200 || response.status === 204) {
                alert("Equipe removida com sucesso!");
                navigate("/gerenciar-projetos", { replace: true });
                return;
            }

            console.warn("Delete retornou status inesperado:", response.status, response.data);
            alert("Projeto excluído (status inesperado). Ver console para detalhes.");
            navigate("/gerenciar-projetos", { replace: true });
        } catch (error) {
            console.error("Erro ao deletar projeto:", error, error.response?.data);
            if (error.response) {
                if (error.response.status === 403) {
                    alert("Você não tem permissão para excluir este projeto.");
                } else if (error.response.status === 404) {
                    alert("Projeto não encontrado (já removido?).");
                    navigate("/gerenciar-projetos", { replace: true });
                } else {
                    alert("Erro ao excluir o projeto. Ver console para mais detalhes.");
                }
            } else {
                alert("Erro de rede ao tentar excluir o projeto.");
            }
        } finally {
            setDeleting(false);
        }
    }

    if (loading) return <div className="gp-container">Carregando...</div>;
    if (!projeto) return <div className="gp-container">Projeto não encontrado.</div>;

    return (
        <div className="gp-container">

            {/* BOTÃO VOLTAR */}
            <button
                onClick={() => navigate("/gerenciar-projetos")}
                className="btn-voltar"
                style={{ marginBottom: "25px" }}
            >
                ⬅ Voltar
            </button>

            <div className="gp-card">
                <h2>{modoEdicao ? "Editar Projeto" : projeto.nome}</h2>

                {/* NOME */}
                <div className="gp-info-group">
                    <div className="gp-info-item">
                        <label className="gp-info-label">Nome do Projeto</label>
                        {modoEdicao ? (
                            <input
                                className="gp-info-value"
                                type="text"
                                name="nome"
                                value={editData.nome}
                                onChange={handleChange}
                            />
                        ) : (
                            <div className="gp-info-value">{projeto.nome}</div>
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
                            <div className="gp-info-value">{projeto.descricao}</div>
                        )}
                    </div>
                </div>

                {/* DATAS */}
                <div className="gp-info-group">
                    <div className="gp-info-item">
                        <label className="gp-info-label">Data de Início</label>
                        {modoEdicao ? (
                            <input
                                className="gp-info-value"
                                type="date"
                                name="dataInicio"
                                value={editData.dataInicio}
                                onChange={handleChange}
                            />
                        ) : (
                            <div className="gp-info-value">{projeto.dataInicio}</div>
                        )}
                    </div>

                    <div className="gp-info-item">
                        <label className="gp-info-label">Deadline</label>
                        {modoEdicao ? (
                            <input
                                className="gp-info-value"
                                type="date"
                                name="deadline"
                                value={editData.deadline}
                                onChange={handleChange}
                            />
                        ) : (
                            <div className="gp-info-value">{projeto.deadline}</div>
                        )}
                    </div>
                </div>

                {/* ORÇAMENTO + STATUS */}
                <div className="gp-info-group">
                    <div className="gp-info-item">
                        <label className="gp-info-label">Orçamento</label>
                        {modoEdicao ? (
                            <input
                                className="gp-info-value"
                                type="number"
                                step="0.01"
                                name="orcamento"
                                value={editData.orcamento}
                                onChange={handleChange}
                            />
                        ) : (
                            <div className="gp-info-value">
                                R$ {Number(projeto.orcamento).toFixed(2)}
                            </div>
                        )}
                    </div>

                    {/* STATUS */}
                    <div className="gp-info-item">
                        <label className="gp-info-label">Status</label>
                        {modoEdicao ? (
                            <select
                                className="gp-info-value"
                                name="status"
                                value={editData.status}
                                onChange={handleChange}
                            >
                                <option value="PLANEJAMENTO">Planejamento</option>
                                <option value="EM_PROGRESSO">Em Progresso</option>
                                <option value="COMPLETO">Completo</option>
                                <option value="CANCELADO">Cancelado</option>
                            </select>
                        ) : (
                            <div className="gp-info-value">{projeto.status}</div>
                        )}
                    </div>
                </div>

                {/* EQUIPE */}
                <div className="gp-info-group">
                    <div className="gp-info-item">
                        <label className="gp-info-label">Equipe Responsável</label>
                        <div className="gp-info-value">{projeto.nomeEquipe}</div>
                    </div>
                </div>

                {/* BOTÕES */}
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
                                Editar
                            </button>
                            <button className="gp-btn gp-delete" onClick={deletar}>
                                Excluir
                            </button>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
}

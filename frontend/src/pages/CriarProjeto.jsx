import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import "./Home.css";
import "./Projetos.css";

export default function CriarProjeto() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        nome: "",
        descricao: "",
        dataInicio: "",
        deadline: "",
        orcamento: "",
        equipe_id: ""
    });

    const [loading, setLoading] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");

    function handleChange(e) {
        setForm({ ...form, [e.target.name]: e.target.value });
    }

    function formatDate(dateStr) {
        const [year, month, day] = dateStr.split("-");
        return `${day}/${month}/${year}`;
    }

    async function handleSubmit(e) {
        e.preventDefault();

        setLoading(true);
        setErrorMsg("");

        try {
            const payload = {
                nome: form.nome,
                descricao: form.descricao,
                dataInicio: formatDate(form.dataInicio),
                deadline: formatDate(form.deadline),
                orcamento: Number(form.orcamento),
                equipe_id: Number(form.equipe_id)
            };

            await api.post("/projetos", payload);

            navigate("/gerenciar-projetos");
        } catch (err) {
            console.error("Erro ao criar projeto:", err);
            setErrorMsg("Não foi possível criar o projeto. Verifique os dados.");
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="home-wrapper">
            <div className="home-content">
                <h1 className="home-title">Criar Novo Projeto</h1>
                <p className="home-description">
                    Preencha os dados abaixo para cadastrar um novo projeto.
                </p>

                {errorMsg && <p className="error-msg">{errorMsg}</p>}

                <form className="projeto-form" onSubmit={handleSubmit}>
                    <label>Nome do Projeto</label>
                    <input
                        type="text"
                        name="nome"
                        value={form.nome}
                        onChange={handleChange}
                        required
                    />

                    <label>Descrição</label>
                    <textarea
                        name="descricao"
                        value={form.descricao}
                        onChange={handleChange}
                        required
                    />

                    <label>Data de Início</label>
                    <input
                        type="date"
                        name="dataInicio"
                        value={form.dataInicio}
                        onChange={handleChange}
                        required
                    />

                    <label>Deadline</label>
                    <input
                        type="date"
                        name="deadline"
                        value={form.deadline}
                        onChange={handleChange}
                        required
                    />

                    <label>Orçamento (R$)</label>
                    <input
                        type="number"
                        step="1.0"
                        name="orcamento"
                        value={form.orcamento}
                        onChange={handleChange}
                        required
                    />

                    <label>ID da Equipe</label>
                    <input
                        type="number"
                        name="equipe_id"
                        value={form.equipe_id}
                        onChange={handleChange}
                        required
                    />

                    <button type="submit" className="projeto-btn" disabled={loading}>
                        {loading ? "Criando..." : "Criar Projeto"}
                    </button>

                    <button
                        type="button"
                        className="projeto-btn voltar-btn"
                        onClick={() => navigate("/gerenciar-projetos")}
                    >
                        Voltar
                    </button>
                </form>
            </div>
        </div>
    );
}

import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import "./Home.css";
import "./Projetos.css";

export default function CriarEquipe() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        nome: "",
        descricao: ""
    });

    const [loading, setLoading] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");

    function handleChange(e) {
        setForm({ ...form, [e.target.name]: e.target.value });
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setLoading(true);
        setErrorMsg("");

        try {
            const payload = {
                nome: form.nome,
                descricao: form.descricao
            };

            await api.post("/equipes", payload);

            navigate("/gerenciar-equipes");
        } catch (err) {
            console.error("Erro ao criar equipe:", err);
            setErrorMsg("Não foi possível criar a equipe. Verifique os dados.");
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="home-wrapper">
            <div className="home-content">
                <h1 className="home-title">Criar Nova Equipe</h1>
                <p className="home-description">
                    Preencha os dados abaixo para cadastrar uma nova equipe.
                </p>

                {errorMsg && <p className="error-msg">{errorMsg}</p>}

                <form className="projeto-form" onSubmit={handleSubmit}>
                    <label>Nome da Equipe</label>
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
                    />

                    <button type="submit" className="projeto-btn" disabled={loading}>
                        {loading ? "Criando..." : "Criar Equipe"}
                    </button>

                    <button
                        type="button"
                        className="projeto-btn voltar-btn"
                        onClick={() => navigate("/gerenciar-equipes")}
                    >
                        Voltar
                    </button>
                </form>
            </div>
        </div>
    );
}

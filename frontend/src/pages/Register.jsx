import { useState } from "react";
import { registrarUsuario } from "../services/registerService";
import Button from "../components/Button";
import { useNavigate } from "react-router-dom";
import icon from "../assets/icon.png";

export default function Registro() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    nome: "",
    email: "",
    senha: "",
    role: "MEMBRO",
  });

  const [erro, setErro] = useState("");
  const [sucesso, setSucesso] = useState("");

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setErro("");

    try {
      await registrarUsuario(form);
      setSucesso("Usuário registrado com sucesso!");
      setTimeout(() => navigate("/login"), 1500);
    } catch (err) {
      setErro(err.message || "Erro ao registrar.");
    }
  }

  return (
    <div
      className="d-flex justify-content-center align-items-center vh-100"
      style={{ background: "#0d1117" }}
    >
      <div className="card p-4 shadow" style={{ width: "350px", background: "#161b22", border: "1px solid #30363d" }}>
        

        <h3 className="text-center mb-3" style={{ color: "white" }}>Registrar</h3>

        {erro && <div className="alert alert-danger">{erro}</div>}
        {sucesso && <div className="alert alert-success">{sucesso}</div>}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label" style={{ color: "#9ab" }}>Nome</label>
            <input
              type="text"
              name="nome"
              placeholder="John Doe"
              value={form.nome}
              onChange={handleChange}
              className="form-control"
              style={{ background: "#0d1117", color: "white", borderColor: "#30363d" }}
            />
          </div>

          <div className="mb-3">
            <label className="form-label" style={{ color: "#9ab" }}>Email</label>
            <input
              type="email"
              name="email"
              placeholder="johndoe@gmail.com"
              value={form.email}
              onChange={handleChange}
              className="form-control"
              style={{ background: "#0d1117", color: "white", borderColor: "#30363d" }}
            />
          </div>

          <div className="mb-3">
            <label className="form-label" style={{ color: "#9ab" }}>Senha</label>
            <input
              type="password"
              name="senha"
              placeholder="••••••••"
              value={form.senha}
              onChange={handleChange}
              className="form-control"
              style={{ background: "#0d1117", color: "white", borderColor: "#30363d" }}
            />
          </div>

          <div className="mb-3">
            <label className="form-label" style={{ color: "#9ab" }}>Função</label>
            <select
              name="role"
              value={form.role}
              onChange={handleChange}
              className="form-select"
              style={{ background: "#0d1117", color: "white", borderColor: "#30363d" }}
            >
              <option value="MEMBRO">MEMBRO</option>
              <option value="GERENTE">GERENTE</option>
            </select>
          </div>

          <Button type="submit">Registrar</Button>

          <div className="text-center mt-3">
            <span style={{ color: "#7e8ba7" }}>Já tem conta?</span>
            <span
              style={{ color: "#0090ff", cursor: "pointer", marginLeft: "5px" }}
              onClick={() => navigate("/login")}
            >
              Entrar
            </span>
          </div>
        </form>
      </div>
    </div>
  );
}

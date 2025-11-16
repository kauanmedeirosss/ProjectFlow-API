import { useState } from "react";
import { useNavigate } from "react-router-dom";
import icon from "../assets/icon.png";
import Button from "../components/Button";

export default function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();

    if (!email || !senha) {
      setErro("Preencha todos os campos.");
      return;
    }

    setErro("");

    try {
      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ login: email, senha }),
      });

      if (response.ok) {
        const data = await response.json();
        // salvar token e role
        localStorage.setItem("tokenJWT", data.tokenJWT);
        localStorage.setItem("role", data.role);

        // redirecionar para home
        navigate("/home");
      } else if (response.status === 400) {
        const errData = await response.json();
        if (errData.fieldErrors && errData.fieldErrors.length > 0) {
          setErro(
            errData.fieldErrors.map(f => `${f.field}: ${f.message}`).join(", ")
          );
        } else {
          setErro(errData.message || "Dados de entrada inválidos.");
        }
      } else if (response.status === 401) {
        setErro("Credenciais inválidas");
      } else {
        setErro("Erro desconhecido. Tente novamente.");
      }
    } catch (err) {
      console.error(err);
      setErro("Falha na conexão com o servidor");
    }
  }

  return (
    <div
      className="d-flex justify-content-center align-items-center vh-100"
      style={{ background: "#0d1117" }}
    >
      <div className="card p-4 shadow" style={{ width: "350px", background: "#161b22", border: "1px solid #30363d" }}>
        
        <div className="text-center mb-3">
          <img
            src={icon}
            alt="Logo"
            style={{ width: "90px", height: "90px" }}
          />
        </div>

        <h3 className="text-center mb-3" style={{ color: "white" }}>ProjectFlow</h3>

        {erro && <div className="alert alert-danger">{erro}</div>}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label" style={{ color: "#9ab" }}>Email</label>
            <input 
              type="email"
              className="form-control"
              placeholder="johndoe@gmail.com"
              value={email}
              onChange={e => setEmail(e.target.value)}
              style={{ background: "#0d1117", color: "white", borderColor: "#30363d" }}
            />
          </div>

          <div className="mb-3">
            <label className="form-label" style={{ color: "#9ab" }}>Senha</label>
            <input 
              type="password"
              className="form-control"
              placeholder="••••••••"
              value={senha}
              onChange={e => setSenha(e.target.value)}
              style={{ background: "#0d1117", color: "white", borderColor: "#30363d" }}
            />
          </div>

          <Button type="submit">
            Entrar
          </Button>

          <div className="text-center mt-3">
            <span style={{ color: "#7e8ba7" }}>Não tem conta?</span>
            <span
              style={{ color: "#0090ff", cursor: "pointer", marginLeft: "5px" }}
              onClick={() => navigate("/register")}
            >
              Criar conta
            </span>
          </div>
        </form>
      </div>
    </div>
  );
}

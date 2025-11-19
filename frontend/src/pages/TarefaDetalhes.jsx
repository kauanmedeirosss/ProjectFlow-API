import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../services/api";

function extractArrayFromResponse(resData) {
  // se já for array, retorna direto
  if (Array.isArray(resData)) return resData;

  // procura chaves comuns: content, conteudo, items, data, results
  const candidateKeys = ["content", "conteudo", "items", "data", "results", "rows"];
  for (const key of candidateKeys) {
    if (resData && Object.prototype.hasOwnProperty.call(resData, key) && Array.isArray(resData[key])) {
      return resData[key];
    }
  }

  // tenta encontrar a primeira propriedade que seja array
  if (resData && typeof resData === "object") {
    for (const k of Object.keys(resData)) {
      if (Array.isArray(resData[k])) return resData[k];
    }
  }

  // fallback vazio
  return [];
}

export default function TarefaDetalhes() {
  const { id } = useParams();
  const [tarefa, setTarefa] = useState(null);
  const [comentarios, setComentarios] = useState([]);
  const [anexos, setAnexos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function carregarDados() {
      console.log("ID recebido pela rota:", id);

      try {
        const tarefaRes = await api.get(`/tarefas/${id}`);
        console.log("Retorno da tarefa:", tarefaRes.data);
        setTarefa(tarefaRes.data);

        const comentariosRes = await api.get(`/tarefas/${id}/comentarios`);
        console.log("Retorno bruto dos comentários:", comentariosRes.data);
        const extractedComentarios = extractArrayFromResponse(comentariosRes.data);
        console.log("Comentários extraídos (array):", extractedComentarios);
        setComentarios(extractedComentarios);

        const anexosRes = await api.get(`/tarefas/${id}/anexos`);
        console.log("Retorno bruto dos anexos:", anexosRes.data);
        const extractedAnexos = extractArrayFromResponse(anexosRes.data);
        console.log("Anexos extraídos (array):", extractedAnexos);
        setAnexos(extractedAnexos);

      } catch (error) {
        console.error("Erro ao carregar detalhes:", error);
      } finally {
        setLoading(false);
      }
    }

    carregarDados();
  }, [id]);

  if (loading) {
    return <p style={{ color: "white", padding: "20px" }}>Carregando...</p>;
  }

  if (!tarefa) {
    return (
      <p style={{ color: "red", padding: "20px" }}>
        Erro: Tarefa não encontrada.
      </p>
    );
  }

  return (
    <div style={{ padding: "20px", color: "white" }}>
      <button
        onClick={() => window.history.back()}
        style={{
          background: "#21262d",
          border: "1px solid #30363d",
          color: "white",
          padding: "8px 14px",
          borderRadius: "8px",
          cursor: "pointer",
          marginBottom: "15px",
          fontSize: "14px",
        }}
        onMouseOver={(e) => (e.target.style.background = "#30363d")}
        onMouseOut={(e) => (e.target.style.background = "#21262d")}
      >
        ⬅ Voltar
      </button>
      <h1 style={{ marginBottom: "10px" }}>{tarefa.titulo}</h1>

      <p><strong>Status:</strong> {tarefa.status}</p>
      <p>
        <strong>Cessionário:</strong>{" "}
        {tarefa.cessionario ? tarefa.cessionario.nome : "Não atribuído"}
      </p>

      <hr style={{ margin: "20px 0", borderColor: "#30363d" }} />

      <h2>Comentários</h2>
      {(!comentarios || comentarios.length === 0) ? (
        <p style={{ color: "#7d8590" }}>Nenhum comentário.</p>
      ) : (
        comentarios.map((c) => (
          <div
            key={c.id}
            style={{
              background: "#161b22",
              border: "1px solid #30363d",
              borderRadius: "10px",
              padding: "12px",
              marginBottom: "10px",
            }}
          >
            <p style={{ margin: 0 }}>{c.conteudo || c.content || c.text || c.body}</p>
            {/* se quiser mostrar autor */}
            {c.autor && c.autor.nome && (
              <small style={{ color: "#9aa4b5", display: "block", marginTop: 6 }}>
                — {c.autor.nome}
              </small>
            )}
          </div>
        ))
      )}

      <hr style={{ margin: "20px 0", borderColor: "#30363d" }} />

      <h2>Anexos</h2>
      {(!anexos || anexos.length === 0) ? (
        <p style={{ color: "#7d8590" }}>Nenhum anexo.</p>
      ) : (
        anexos.map((a) => (
          <div
            key={a.id}
            style={{
              background: "#0d1117",
              border: "1px solid #21262d",
              borderRadius: "10px",
              padding: "10px",
              marginBottom: "10px",
            }}
          >
            <p style={{ margin: 0 }}>
              <strong>{a.nomeArquivo || a.name || a.filename}</strong>
            </p>
            <a
              href={a.URLarquivo || a.urlArquivo || a.url || a.link}
              target="_blank"
              rel="noreferrer"
              style={{ color: "#58a6ff" }}
            >
              Abrir arquivo
            </a>
          </div>
        ))
      )}
    </div>
  );
}

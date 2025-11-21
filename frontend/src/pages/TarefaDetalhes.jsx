import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import api from "../services/api";

function extractArrayFromResponse(resData) {
  if (Array.isArray(resData)) return resData;

  const candidateKeys = ["content", "conteudo", "items", "data", "results", "rows"];
  for (const key of candidateKeys) {
    if (resData && Object.prototype.hasOwnProperty.call(resData, key) && Array.isArray(resData[key])) {
      return resData[key];
    }
  }

  if (resData && typeof resData === "object") {
    for (const k of Object.keys(resData)) {
      if (Array.isArray(resData[k])) return resData[k];
    }
  }

  return [];
}

export default function TarefaDetalhes() {
  const { id } = useParams();
  const [tarefa, setTarefa] = useState(null);
  const [comentarios, setComentarios] = useState([]);
  const [novoComentario, setNovoComentario] = useState("");
  const [anexos, setAnexos] = useState([]);
  const [loading, setLoading] = useState(true);
  const { user } = useAuth();

  // Estados de edição
  const [editandoId, setEditandoId] = useState(null);
  const [novoConteudo, setNovoConteudo] = useState("");

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [addError, setAddError] = useState(null);

  useEffect(() => {
    async function carregarDados() {
      try {
        const tarefaRes = await api.get(`/tarefas/${id}`);
        setTarefa(tarefaRes.data);

        const comentariosRes = await api.get(`/tarefas/${id}/comentarios`);
        const extractedComentarios = extractArrayFromResponse(comentariosRes.data);
        setComentarios(extractedComentarios);

        const anexosRes = await api.get(`/tarefas/${id}/anexos`);
        const extractedAnexos = extractArrayFromResponse(anexosRes.data);
        setAnexos(extractedAnexos);

      } catch (error) {
        console.error("Erro ao carregar detalhes:", error);
      } finally {
        setLoading(false);
      }
    }

    carregarDados();
  }, [id]);

  async function deletarComentario(idComentario) {
    if (!window.confirm("Tem certeza que deseja excluir este comentário?")) return;

    try {
      await api.delete(`/comentarios/${idComentario}`);
      setComentarios((prev) => prev.filter((c) => c.id !== idComentario));
    } catch (error) {
      console.error("Erro ao deletar comentário:", error);
      alert("Erro ao deletar comentário!");
    }
  }

  // === ADICIONAR COMENTÁRIO ===
  async function handleAddComentario() {
    setAddError(null);

    if (novoComentario.trim() === "") {
      setAddError("Digite um comentário.");
      return;
    }

    const usuarioLogado = user;

    if (!usuarioLogado || !usuarioLogado.id) {
      setAddError("Usuário não identificado. Faça login novamente.");
      return;
    }

    const dto = {
      conteudo: novoComentario,
      tarefa_id: Number(id),
      autor_id: usuarioLogado.id,
    };

    setIsSubmitting(true);
    try {
      const response = await api.post("/comentarios", dto);

      if (response?.data?.id) {
        setComentarios((prev) => [...prev, response.data]);
      } else {
        const comentariosRes = await api.get(`/tarefas/${id}/comentarios`);
        setComentarios(extractArrayFromResponse(comentariosRes.data));
      }

      setNovoComentario("");
    } catch (error) {
      console.error("Erro ao adicionar comentário (detalhes):", error);
      setAddError("Erro ao adicionar comentário.");
    } finally {
      setIsSubmitting(false);
    }
  }

  // === INICIAR EDIÇÃO ===
  function iniciarEdicao(comentario) {
    setEditandoId(comentario.id);
    setNovoConteudo(comentario.conteudo);
  }

  // === CANCELAR EDIÇÃO ===
  function cancelarEdicao() {
    setEditandoId(null);
    setNovoConteudo("");
  }

  // === SALVAR EDIÇÃO ===
  async function salvarEdicao(idComentario) {
    if (novoConteudo.trim().length < 1) {
      alert("Digite algum conteúdo.");
      return;
    }

    try {
      const dto = { conteudo: novoConteudo };

      await api.put(`/comentarios/${idComentario}`, dto);

      setComentarios((prev) =>
        prev.map((c) =>
          c.id === idComentario ? { ...c, conteudo: novoConteudo } : c
        )
      );

      cancelarEdicao();

    } catch (error) {
      console.error("Erro ao atualizar comentário:", error);
      alert("Erro ao atualizar o comentário.");
    }
  }

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

      {/* Voltar */}
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

      {/* === COMENTÁRIOS === */}
      <h2>Comentários</h2>

      {/* Caixa de adicionar comentário */}
      <div style={{
        backgroundColor: "#13161b",
        padding: "16px",
        borderRadius: 8,
        marginBottom: 12
      }}>
        <div style={{ display: "flex", gap: 12, alignItems: "center" }}>
          <textarea
            placeholder="Adicionar comentário..."
            style={{
              flex: 1,
              backgroundColor: "#0d0f12",
              color: "white",
              padding: 10,
              borderRadius: 6,
              border: "1px solid #2a2f36",
              height: 70,
              resize: "none"
            }}
            value={novoComentario}
            onChange={(e) => setNovoComentario(e.target.value)}
            disabled={isSubmitting}
          />

          <button
            onClick={handleAddComentario}
            disabled={isSubmitting}
            style={{
              backgroundColor: isSubmitting ? "#2e7d32" : "#28a745",
              color: "white",
              padding: "10px 20px",
              borderRadius: 6,
              border: "none",
              cursor: isSubmitting ? "not-allowed" : "pointer",
              fontSize: 15,
            }}
          >
            {isSubmitting ? "Enviando..." : "Adicionar"}
          </button>
        </div>

        {addError && (
          <div style={{ marginTop: 8, color: "#ffb3b3", fontSize: 13 }}>
            {String(addError)}
          </div>
        )}
      </div>

      {/* Lista de comentários */}
      {(!comentarios || comentarios.length === 0) ? (
        <p style={{ color: "#7d8590" }}>Nenhum comentário.</p>
      ) : (
        comentarios.map((c) => (
          <div
            key={c.id}
            style={{
              background: "#161b22",
              border: "1px solid #30363d",
              borderRadius: "8px",
              padding: "12px 16px",
              marginBottom: "10px",
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              position: "relative",
              minHeight: "50px"
            }}
          >

            {editandoId === c.id ? (
              <>
                <textarea
                  style={{
                    width: "100%",
                    backgroundColor: "#0d0f12",
                    color: "white",
                    padding: 10,
                    borderRadius: 6,
                    border: "1px solid #2a2f36",
                    resize: "none"
                  }}
                  value={novoConteudo}
                  onChange={(e) => setNovoConteudo(e.target.value)}
                />

                <div style={{ marginTop: 10, display: "flex", gap: 10 }}>
                  <button
                    onClick={() => salvarEdicao(c.id)}
                    style={{
                      backgroundColor: "#238636",
                      color: "white",
                      padding: "6px 12px",
                      borderRadius: 6,
                      border: "none",
                      cursor: "pointer"
                    }}
                  >
                    Salvar
                  </button>

                  <button
                    onClick={cancelarEdicao}
                    style={{
                      backgroundColor: "#6c757d",
                      color: "white",
                      padding: "6px 12px",
                      borderRadius: 6,
                      border: "none",
                      cursor: "pointer"
                    }}
                  >
                    Cancelar
                  </button>
                </div>
              </>
            ) : (
              <>
                <p style={{ margin: 0 }}>{c.conteudo}</p>

                {c.autor && (
                  <small style={{ color: "#9aa4b5", display: "block", marginTop: 6 }}>
                    — {c.autor}
                  </small>
                )}

                <div style={{ display: "flex", justifyContent: "flex-end", gap: "10px", marginTop: "10px" }}>
                  <button
                    onClick={() => iniciarEdicao(c)}
                    style={{
                      backgroundColor: "#0d6efd",
                      border: "none",
                      padding: "6px 14px",
                      borderRadius: "6px",
                      color: "white",
                      cursor: "pointer",
                      fontSize: "14px",
                    }}
                  >
                    Editar
                  </button>

                  <button
                    onClick={() => deletarComentario(c.id)}
                    style={{
                      backgroundColor: "#dc3545",
                      border: "none",
                      padding: "6px 14px",
                      borderRadius: "6px",
                      color: "white",
                      cursor: "pointer",
                      fontSize: "14px",
                    }}
                  >
                    Excluir
                  </button>
                </div>
              </>
            )}
          </div>
        ))
      )}

      <hr style={{ margin: "20px 0", borderColor: "#30363d" }} />

      {/* === ANEXOS === */}
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
              <strong>{a.nomeArquivo}</strong>
            </p>
            <a
              href={a.URLarquivo}
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

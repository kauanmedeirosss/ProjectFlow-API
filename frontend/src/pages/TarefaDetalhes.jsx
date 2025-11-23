import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import api from "../services/api";
import "./TarefaDetalhes.css";

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
  const [novoNomeArquivo, setNovoNomeArquivo] = useState("");
  const [novoURLArquivo, setNovoURLArquivo] = useState("");
  const [anexoErro, setAnexoErro] = useState(null);
  const [isEnviandoAnexo, setIsEnviandoAnexo] = useState(false);
  const [editandoId, setEditandoId] = useState(null);
  const [novoConteudo, setNovoConteudo] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [addError, setAddError] = useState(null);
  const [editandoAnexoId, setEditandoAnexoId] = useState(null);
  const [editNomeArquivo, setEditNomeArquivo] = useState("");
  const [editURLArquivo, setEditURLArquivo] = useState("");


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

  function iniciarEdicao(comentario) {
    setEditandoId(comentario.id);
    setNovoConteudo(comentario.conteudo);
  }

  function cancelarEdicao() {
    setEditandoId(null);
    setNovoConteudo("");
  }

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

  async function handleAddAnexo() {
    setAnexoErro(null);

    if (novoNomeArquivo.trim() === "" || novoURLArquivo.trim() === "") {
      setAnexoErro("Preencha todos os campos.");
      return;
    }

    const dto = {
      nomeArquivo: novoNomeArquivo,
      URLarquivo: novoURLArquivo,
      tarefa_id: Number(id)
    };

    setIsEnviandoAnexo(true);

    try {
      const resp = await api.post("/anexos", dto);

      const anexosRes = await api.get(`/tarefas/${id}/anexos`);
      setAnexos(extractArrayFromResponse(anexosRes.data));

      setNovoNomeArquivo("");
      setNovoURLArquivo("");

    } catch (error) {
      console.error("Erro ao adicionar anexo:", error);
      setAnexoErro("Erro ao adicionar anexo.");
    } finally {
      setIsEnviandoAnexo(false);
    }
  }

  async function handleDeleteAnexo(anexoId) {
    try {
      await api.delete(`/anexos/${anexoId}`);

      const anexosRes = await api.get(`/tarefas/${id}/anexos`);
      setAnexos(extractArrayFromResponse(anexosRes.data));

    } catch (err) {
      console.error("Erro ao excluir anexo:", err);
      alert("Erro ao excluir anexo.");
    }
  }

  function iniciarEdicaoAnexo(anexo) {
    setEditandoAnexoId(anexo.id);
    setEditNomeArquivo(anexo.nomeArquivo);
    setEditURLArquivo(anexo.URLarquivo);
  }

  async function salvarEdicaoAnexo() {
    try {
      const dto = {
        id: editandoAnexoId,
        nomeArquivo: editNomeArquivo,
        URLarquivo: editURLArquivo
      };

      await api.put("/anexos", dto);
      const anexosRes = await api.get(`/tarefas/${id}/anexos`);
      setAnexos(extractArrayFromResponse(anexosRes.data));

      setEditandoAnexoId(null);
      setEditNomeArquivo("");
      setEditURLArquivo("");

    } catch (err) {
      console.error("Erro ao atualizar anexo:", err);
      alert("Erro ao atualizar anexo.");
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
    <div className="tarefa-detalhes-container">
      {/* Botão Voltar */}
      <button
        onClick={() => window.history.back()}
        className="btn-voltar"
      >
        ⬅ Voltar
      </button>

      {/* Header da Tarefa */}
      <div className="tarefa-header">
        <h1 className="tarefa-titulo">{tarefa.titulo}</h1>

        <div className="tarefa-info">
          <div className="info-item">
            <span className="info-label">Status</span>
            <span className="info-value">{tarefa.status}</span>
          </div>

          <div className="info-item">
            <span className="info-label">Cessionário</span>
            <span className="info-value">
              {tarefa.cessionario ? tarefa.cessionario : "Não atribuído"}
            </span>
          </div>

          <div className="info-item">
            <span className="info-label">Prioridade</span>
            <span className="info-value">{tarefa.prioridade || "Não informada"}</span>
          </div>

          <div className="info-item">
            <span className="info-label">Horas Estimadas</span>
            <span className="info-value">{tarefa.horasEstimadas ?? "--"}</span>
          </div>
        </div>
      </div>

      {/* Seção de Comentários */}
      <h2 className="secao-titulo">Comentários</h2>

      {/* Formulário de Comentário */}
      <div className="comentario-form">
        <div className="comentario-input-container">
          <textarea
            placeholder="Adicionar comentário..."
            className="comentario-textarea"
            value={novoComentario}
            onChange={(e) => setNovoComentario(e.target.value)}
            disabled={isSubmitting}
          />
          <button
            onClick={handleAddComentario}
            disabled={isSubmitting}
            className="btn-adicionar"
          >
            {isSubmitting ? "Enviando..." : "Adicionar"}
          </button>
        </div>
        {addError && <div className="erro-mensagem">{String(addError)}</div>}
      </div>

      {/* Lista de Comentários */}
      {(!comentarios || comentarios.length === 0) ? (
        <p className="empty-text">Nenhum comentário.</p>
      ) : (
        <div className="comentarios-lista">
          {comentarios.map((c) => (
            <div key={c.id} className="comentario-item">
              {editandoId === c.id ? (
                <>
                  <textarea
                    className="comentario-textarea"
                    value={novoConteudo}
                    onChange={(e) => setNovoConteudo(e.target.value)}
                    style={{ minHeight: "120px", marginBottom: "15px" }}
                  />
                  <div style={{ display: "flex", gap: "10px" }}>
                    <button
                      onClick={() => salvarEdicao(c.id)}
                      className="btn-salvar"
                    >
                      Salvar
                    </button>
                    <button
                      onClick={cancelarEdicao}
                      className="btn-cancelar"
                    >
                      Cancelar
                    </button>
                  </div>
                </>
              ) : (
                <>
                  <p className="comentario-conteudo">{c.conteudo}</p>
                  {c.autor && (
                    <p className="comentario-autor">— {c.autor}</p>
                  )}
                  <div className="comentario-acoes">
                    <button
                      onClick={() => iniciarEdicao(c)}
                      className="btn-editar"
                    >
                      Editar
                    </button>
                    <button
                      onClick={() => deletarComentario(c.id)}
                      className="btn-excluir"
                    >
                      Excluir
                    </button>
                  </div>
                </>
              )}
            </div>
          ))}
        </div>
      )}

      {/* Seção de Anexos */}
      <h2 className="secao-titulo">Anexos</h2>

      {/* Formulário de Anexo */}
      <div className="anexo-form">
        <div className="anexo-inputs">
          <input
            type="text"
            placeholder="Nome do arquivo"
            className="anexo-input"
            value={novoNomeArquivo}
            onChange={(e) => setNovoNomeArquivo(e.target.value)}
          />
          <input
            type="text"
            placeholder="URL do arquivo"
            className="anexo-input"
            value={novoURLArquivo}
            onChange={(e) => setNovoURLArquivo(e.target.value)}
          />
        </div>
        <button
          onClick={handleAddAnexo}
          disabled={isEnviandoAnexo}
          className="btn-adicionar"
        >
          {isEnviandoAnexo ? "Enviando..." : "Adicionar Anexo"}
        </button>
        {anexoErro && <div className="erro-mensagem">{String(anexoErro)}</div>}
      </div>

      {/* Lista de Anexos */}
      {!anexos || anexos.length === 0 ? (
        <p className="empty-text">Nenhum anexo.</p>
      ) : (
        <div className="anexos-lista">
          {anexos.map((anexo) => (
            <div key={anexo.id} className="anexo-item">
              {editandoAnexoId === anexo.id ? (
                <div className="anexo-inputs">
                  <input
                    className="anexo-input"
                    value={editNomeArquivo}
                    onChange={(e) => setEditNomeArquivo(e.target.value)}
                  />
                  <input
                    className="anexo-input"
                    value={editURLArquivo}
                    onChange={(e) => setEditURLArquivo(e.target.value)}
                  />
                  <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
                    <button
                      onClick={salvarEdicaoAnexo}
                      className="btn-salvar"
                    >
                      Salvar
                    </button>
                    <button
                      onClick={() => setEditandoAnexoId(null)}
                      className="btn-cancelar"
                    >
                      Cancelar
                    </button>
                  </div>
                </div>
              ) : (
                <>
                  <div className="anexo-info">
                    <h4 className="anexo-nome">{anexo.nomeArquivo}</h4>
                    <a
                      href={anexo.URLarquivo}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="anexo-url"
                    >
                      {anexo.URLarquivo}
                    </a>
                  </div>
                  <div className="anexo-acoes">
                    <button
                      onClick={() => iniciarEdicaoAnexo(anexo)}
                      className="btn-editar"
                    >
                      Editar
                    </button>
                    <button
                      onClick={() => handleDeleteAnexo(anexo.id)}
                      className="btn-excluir"
                    >
                      Excluir
                    </button>
                  </div>
                </>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

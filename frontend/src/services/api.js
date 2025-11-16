import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080", // Ajuste se necessário
});

// Intercepta erros e retorna a mensagem correta
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const message =
      error.response?.data?.message ||
      error.response?.data?.error ||
      "Erro ao processar requisição.";
    return Promise.reject({ ...error, message });
  }
);

export default api;

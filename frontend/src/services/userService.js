import api from "./api";

export async function registerUser({ nome, email, senha, role }) {
  const body = { nome, email, senha, role };

  const response = await api.post("/usuarios", body);

  return response.data;
}

import axios from "axios";

const API_URL = "http://localhost:8080";

export async function registrarUsuario(usuario) {
  try {
    const response = await axios.post(`${API_URL}/usuarios`, usuario, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return response.data; // retorna UsuarioRetornoDTO
  } catch (error) {
    // erro de validação vem em error.response.data
    throw error.response?.data || { message: "Erro desconhecido" };
  }
}

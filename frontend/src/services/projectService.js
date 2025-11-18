import api from "./api";

const getMyProjects = async () => {
  try {
    const response = await api.get("/projetos/meus");
    return response.data;
  } catch (error) {
    console.error("Erro ao buscar projetos do usuário:", error);
    throw error;
  }
};

export default {
  getMyProjects,
};

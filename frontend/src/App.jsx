import { useState } from "react";
import { Routes, Route } from "react-router-dom";
import SplashScreen from "./pages/SplashScreen";
import Login from "./pages/Login";
import Register from "./pages/Register";
import HomeAdmin from "./pages/HomeAdmin";
import HomeMembro from "./pages/HomeMembro";
import { AuthProvider } from "./context/AuthContext";
import PrivateRoute from "./utils/PrivateRoute";
import MeusProjetos from "./pages/MeusProjetos";
import TarefasProjeto from "./pages/TarefasProjeto";
import TarefaDetalhes from "./pages/TarefaDetalhes";
import MinhasTarefas from "./pages/MinhasTarefas";
import GerenciarProjetos from "./pages/GerenciarProjetos";
import GerenciarEquipes from "./pages/GerenciarEquipes";
import CriarProjeto from "./pages/CriarProjeto";
import GerenciarProjeto from "./pages/GerenciarProjeto";
import GerenciarEquipe from "./pages/GerenciarEquipe";
import CriarEquipe from "./pages/CriarEquipe";
import Dashboard from "./pages/Dashboard";

export default function App() {
  const [showSplash, setShowSplash] = useState(true);

  if (showSplash) {
    return <SplashScreen onFinish={() => setShowSplash(false)} />;
  }

  return (
    <AuthProvider>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Rotas protegidas */}
        <Route element={<PrivateRoute allowedRoles={["ADMIN", "GERENTE"]} />}>
          <Route path="/home" element={<HomeAdmin />} />
          <Route path="/gerenciar-projetos" element={<GerenciarProjetos />} />
          <Route path="/gerenciar-equipes" element={<GerenciarEquipes />} />
          <Route path="/projetos/criar" element={<CriarProjeto />} />
          <Route path="/projetos/:id" element={<GerenciarProjeto />} />
          <Route path="/equipes/:id" element={<GerenciarEquipe />} />
          <Route path="/equipes/criar" element={<CriarEquipe />} />
          <Route path="/dashboard" element={<Dashboard />} />
        </Route>

        <Route element={<PrivateRoute allowedRoles={["MEMBRO"]} />}>
          <Route path="/home-membro" element={<HomeMembro />} />
          <Route path="/meus-projetos" element={<MeusProjetos />} />
          <Route path="/projetos/:id/tarefas" element={<TarefasProjeto />} />
          <Route path="/tarefas/:id" element={<TarefaDetalhes />} />
          <Route path="/minhas-tarefas" element={<MinhasTarefas />} />
        </Route>

      </Routes>
    </AuthProvider>
  );
}

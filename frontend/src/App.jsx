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
import GerenciarUsuarios from "./pages/GerenciarUsuarios";
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
          <Route path="/gerenciar-usuarios" element={<GerenciarUsuarios />} />
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

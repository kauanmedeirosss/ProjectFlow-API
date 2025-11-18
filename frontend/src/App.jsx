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
import ProjetoDetalhes from "./pages/ProjetoDetalhes";
import TarefasProjeto from "./pages/TarefasProjeto";


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
        </Route>

        <Route element={<PrivateRoute allowedRoles={["MEMBRO"]} />}>
          <Route path="/home-membro" element={<HomeMembro />} />
          <Route path="/meus-projetos" element={<MeusProjetos />} />
          <Route path="/projetos/:id" element={<ProjetoDetalhes />} />
          <Route path="/projetos/:id/tarefas" element={<TarefasProjeto />} />
        </Route>

      </Routes>
    </AuthProvider>
  );
}

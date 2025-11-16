import { useState } from "react";
import { Routes, Route } from "react-router-dom";
import SplashScreen from "./pages/SplashScreen";
import Login from "./pages/Login";
import Register from "./pages/Register";
import HomeAdmin from "./pages/HomeAdmin";
import HomeMembro from "./pages/HomeMembro";
import { AuthProvider } from "./context/AuthContext";
import PrivateRoute from "./utils/PrivateRoute";

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
        </Route>
      </Routes>
    </AuthProvider>
  );
}

import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function PrivateRoute() {
  const { token, loading } = useAuth();

  if (loading) return <div>Carregando...</div>; // opcional: splash ou loader

  return token ? <Outlet /> : <Navigate to="/login" replace />;
}

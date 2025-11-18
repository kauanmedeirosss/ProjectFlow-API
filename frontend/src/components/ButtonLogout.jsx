import React from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function ButtonLogout() {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();          // limpa token e usuário
    navigate("/login"); // redireciona
  };

  return (
    <button
      onClick={handleLogout}
      style={{
        backgroundColor: "#0090ff",
        color: "white",
        border: "none",
        borderRadius: "8px",
        padding: "8px 16px",
        cursor: "pointer",
        marginLeft: "auto",
        marginRight: "15px",
        transition: "background 0.2s",
      }}
      onMouseEnter={(e) =>
        (e.currentTarget.style.backgroundColor = "#007acc")
      }
      onMouseLeave={(e) =>
        (e.currentTarget.style.backgroundColor = "#0090ff")
      }
    >
      Logout
    </button>
  );
}

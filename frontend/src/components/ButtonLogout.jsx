import React from "react";

export default function ButtonLogout({ onClick }) {
  return (
    <button
      onClick={onClick}
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
      onMouseEnter={e => (e.currentTarget.style.backgroundColor = "#007acc")}
      onMouseLeave={e => (e.currentTarget.style.backgroundColor = "#0090ff")}
    >
      Logout
    </button>
  );
}
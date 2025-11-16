import React from "react";

export default function UserProfileCard({ nome, email, role }) {
  return (
    <div
      style={{
        background: "#161b22",
        padding: "20px",
        borderRadius: "12px",
        border: "1px solid #30363d",
        color: "white",
        width: "100%",
        maxWidth: "400px",
      }}
    >
      <h3 style={{ marginBottom: "10px" }}>{nome}</h3>
      <p style={{ marginBottom: "5px" }}>Email: {email}</p>
      <p style={{ marginBottom: "0px" }}>Role: {role}</p>
    </div>
  );
}
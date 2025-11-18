import React from "react";

export default function UserProfileCard({ nome, email, role }) {
  return (
    <div className="profile-card">
      <div className="profile-header">
        <div className="avatar-circle">
          {nome.charAt(0).toUpperCase()}
        </div>

        <div>
          <h2 className="profile-name">{nome}</h2>
        </div>
      </div>

      <div className="profile-info">
        <p><strong>Email:</strong> {email}</p>
        <p><strong>Função:</strong> {role}</p>
      </div>
    </div>
  );
}

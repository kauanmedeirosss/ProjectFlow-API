import React from 'react';
import { Link } from 'react-router-dom';

export const Dashboard: React.FC = () => {
  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Dashboard</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <Link
          to="/projetos"
          className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow"
        >
          <h3 className="text-lg font-semibold text-gray-900">Projetos</h3>
          <p className="text-gray-600 mt-2">Gerencie seus projetos</p>
        </Link>

        <Link
          to="/tarefas"
          className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow"
        >
          <h3 className="text-lg font-semibold text-gray-900">Tarefas</h3>
          <p className="text-gray-600 mt-2">Visualize e gerencie tarefas</p>
        </Link>

        <Link
          to="/equipes"
          className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow"
        >
          <h3 className="text-lg font-semibold text-gray-900">Equipes</h3>
          <p className="text-gray-600 mt-2">Gerencie equipes e membros</p>
        </Link>
      </div>
    </div>
  );
};
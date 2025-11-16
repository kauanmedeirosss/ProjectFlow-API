import { useEffect } from "react";
import icon from "../assets/icon.png";

export default function SplashScreen({ onFinish }) {
  useEffect(() => {
    const timer = setTimeout(onFinish, 1600);
    return () => clearTimeout(timer);
  }, [onFinish]);

  return (
    <div
      className="d-flex flex-column justify-content-center align-items-center vh-100"
      style={{ backgroundColor: "#0d1117" }}
    >
      <img
        src={icon}
        alt="ProjectFlow Logo"
        style={{
          width: "120px",
          height: "120px",
          animation: "pulse 1.5s ease-in-out infinite"
        }}
      />

      <h1
        style={{
          marginTop: "10px",
          fontWeight: "600",
          color: "#fff",
          animation: "fadeIn 1s ease-in-out",
        }}
      >
        ProjectFlow
      </h1>

      <style>
        {`
          @keyframes pulse {
            0% { transform: scale(1); opacity: 0.7; }
            50% { transform: scale(1.12); opacity: 1; }
            100% { transform: scale(1); opacity: 0.7; }
          }

          @keyframes fadeIn {
            from { opacity: 0; transform: translateY(5px); }
            to { opacity: 1; transform: translateY(0); }
          }
        `}
      </style>
    </div>
  );
}

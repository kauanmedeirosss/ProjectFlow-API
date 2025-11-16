import "./Button.css";

export default function Button({ children, onClick, type = "button", className = "" }) {
  return (
    <button
      type={type}
      onClick={onClick}
      className={`pf-btn ${className}`}
    >
      {children}
    </button>
  );
}

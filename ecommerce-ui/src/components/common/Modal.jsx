import React from "react";

export default function Modal({ children, onClose, className }) {
  return (
    <div className={className?.overlay || "modalOverlay"}>
      <div className={className?.modal || "modal"}>
        {children}
      </div>
    </div>
  );
}

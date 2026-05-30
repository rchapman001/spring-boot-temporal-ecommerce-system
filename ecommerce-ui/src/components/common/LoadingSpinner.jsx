import React from "react";

export default function LoadingSpinner({ message = "Loading..." }) {
  return (
    <div style={{ padding: 16, textAlign: "center" }}>
      <div style={{ marginBottom: 8 }}>{message}</div>
      <div style={{ height: 4, background: "rgba(255,255,255,0.06)", borderRadius: 2 }}>
        <div style={{ width: "40%", height: "100%", background: "#6366f1" }} />
      </div>
    </div>
  );
}

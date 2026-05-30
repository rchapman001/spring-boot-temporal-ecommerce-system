import React from "react";

export default function ErrorMessage({ error }) {
  return (
    <div style={{ padding: 12, background: "#7f1d1d", borderRadius: 8, color: "white" }}>
      {typeof error === "string" ? error : JSON.stringify(error)}
    </div>
  );
}

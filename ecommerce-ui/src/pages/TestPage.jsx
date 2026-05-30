import { useState } from "react";
import common from "../components/styles/Common.module.css";
import { startWorkflow } from "../services/orderService";

export default function TestPage() {
  const [response, setResponse] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const runWorkflow = async (workflowName) => {
    setLoading(true);
    setResponse("");
    setError("");

    try {
      const res = await startWorkflow(workflowName, { workflowName });
      setResponse(JSON.stringify(res, null, 2));
    } catch (err) {
      setError(`Failed to execute ${workflowName}.`);
    }

    setLoading(false);
  };

  return (
    <div className={common.card}>
      <h1 className={common.title}>Workflow Tester</h1>
      <p className={common.description}>
        Execute backend workflows and view responses instantly.
      </p>

      <div className={common.buttonGroup}>
        <button
          onClick={() => runWorkflow("TestWorkflow")}
          disabled={loading}
          className={common.button}
        >
          {loading ? "Running..." : "Run TestWorkflow"}
        </button>

        <button
          onClick={() => runWorkflow("TestWorkflow2")}
          disabled={loading}
          className={common.button}
        >
          {loading ? "Running..." : "Run TestWorkflow2"}
        </button>
      </div>

      {error && <div className={common.error}>{error}</div>}

      {response && (
        <div className={common.responseContainer}>
          <h3>Response</h3>
          <pre className={common.response}>{response}</pre>
        </div>
      )}
    </div>
  );
}
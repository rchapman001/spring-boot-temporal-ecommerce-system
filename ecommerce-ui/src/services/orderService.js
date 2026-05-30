import http from "./http";

export async function startWorkflow(workflowName, payload) {
  const res = await http.post(`/workflows/start-sync/${workflowName}`, payload);
  return res.data;
}

export async function getOrders(baseOrderApi) {
  // fallback: caller can provide a full URL; keep this simple
  const res = await http.get(baseOrderApi || "/orders");
  return res.data;
}

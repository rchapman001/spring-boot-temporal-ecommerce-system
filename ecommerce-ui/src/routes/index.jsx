import { Routes, Route } from "react-router-dom";
import ProductPage from "../pages/ProductPage";
import AdminPage from "../pages/AdminPage";
import TestPage from "../pages/TestPage";

export default function AppRoutes() {
  return (
    <Routes>
      <Route
        path="/"
        element={
          <div>
            <h1>Welcome</h1>
            <p>
              Workflow orchestration console powered by Temporal.
            </p>
          </div>
        }
      />
      <Route path="/products" element={<ProductPage />} />
      <Route path="/admin" element={<AdminPage />} />
      <Route path="/test" element={<TestPage />} />
    </Routes>
  );
}

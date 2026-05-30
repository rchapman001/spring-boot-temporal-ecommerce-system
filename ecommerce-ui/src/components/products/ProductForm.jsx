import React, { useState } from "react";
import { startWorkflow } from "../../services/orderService";
import { CREATE_WORKFLOW_NAME } from "../../api";
import styles from "../styles/AdminPage.module.css";
import toast from "react-hot-toast";

export default function ProductForm({ onCreated }) {
  const [form, setForm] = useState({ name: "", description: "", price: "", quantityAvailable: "", quantityReserved: "" });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((p) => ({ ...p, [name]: value }));
  };

  const submit = async () => {
    if (!form.name || !form.price || !form.quantityAvailable) {
      toast.error("Name, price, and quantity are required.");
      return;
    }

    setLoading(true);
    try {
      const payload = {
        name: form.name,
        description: form.description,
        price: parseFloat(form.price),
        quantityAvailable: parseInt(form.quantityAvailable),
        quantityReserved: parseInt(form.quantityReserved || 0),
      };

      await startWorkflow(CREATE_WORKFLOW_NAME, payload);
      toast.success("Product created");
      setForm({ name: "", description: "", price: "", quantityAvailable: "", quantityReserved: "" });
      onCreated?.();
    } catch (err) {
      toast.error(err?.message || "Failed to create product.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>Create Product</h2>
      <div className={styles.formGroup}>
        <div className={styles.field}>
          <label className={styles.label}>Product Name</label>
          <input name="name" value={form.name} onChange={handleChange} className={styles.input} />
        </div>
        <div className={styles.field}>
          <label className={styles.label}>Description</label>
          <textarea name="description" value={form.description} onChange={handleChange} className={styles.textarea} />
        </div>
        <div className={styles.field}>
          <label className={styles.label}>Price</label>
          <input name="price" type="number" value={form.price} onChange={handleChange} className={styles.input} />
        </div>
        <div className={styles.field}>
          <label className={styles.label}>Quantity Available</label>
          <input name="quantityAvailable" type="number" value={form.quantityAvailable} onChange={handleChange} className={styles.input} />
        </div>
        <div className={styles.field}>
          <label className={styles.label}>Quantity Reserved</label>
          <input name="quantityReserved" type="number" value={form.quantityReserved} onChange={handleChange} className={styles.input} />
        </div>
      </div>
      <div style={{ marginTop: 12 }}>
        <button className="button" onClick={submit} disabled={loading}>{loading ? "Creating..." : "Create Product"}</button>
      </div>
    </div>
  );
}

import React, { useState } from "react";
import * as productService from "../../services/productService";
import * as inventoryService from "../../services/inventoryService";
import styles from "../styles/AdminPage.module.css";
import toast from "react-hot-toast";

export default function ProductEditor({ product, onSaved, onDeleted }) {
  const [local, setLocal] = useState({ ...product });
  const [saving, setSaving] = useState(false);

  const handleChange = (field, value) => setLocal((p) => ({ ...p, [field]: value }));

  const save = async () => {
    setSaving(true);
    try {
      await productService.updateProduct(local.id, { name: local.name, description: local.description, price: parseFloat(local.price) });
      await inventoryService.updateInventory(local.id, { productId: local.id, quantityAvailable: parseInt(local.quantityAvailable || 0), quantityReserved: parseInt(local.quantityReserved || 0) });
      toast.success("Product updated");
      onSaved?.();
    } catch (err) {
      toast.error(err?.message || "Failed to update product");
    } finally {
      setSaving(false);
    }
  };

  const remove = async () => {
    if (!window.confirm("Delete this product?")) return;
    try {
      await onDeleted?.(local.id);
      toast.success("Delete requested");
    } catch (err) {
      toast.error(err?.message || "Failed to delete product");
    }
  };

  return (
    <div className={styles.productCard}>
      <div className={styles.field}>
        <label className={styles.label}>Product Name</label>
        <input value={local.name} onChange={(e) => handleChange("name", e.target.value)} className={styles.input} />
      </div>

      <div className={styles.field}>
        <label className={styles.label}>Description</label>
        <textarea value={local.description} onChange={(e) => handleChange("description", e.target.value)} className={styles.textarea} />
      </div>

      <div className={styles.field}>
        <label className={styles.label}>Price</label>
        <input type="number" value={local.price} onChange={(e) => handleChange("price", e.target.value)} className={styles.input} />
      </div>

      <div className={styles.field}>
        <label className={styles.label}>Quantity Available</label>
        <input type="number" value={local.quantityAvailable} onChange={(e) => handleChange("quantityAvailable", e.target.value)} className={styles.input} />
      </div>

      <div className={styles.field}>
        <label className={styles.label}>Quantity Reserved</label>
        <input type="number" value={local.quantityReserved} onChange={(e) => handleChange("quantityReserved", e.target.value)} className={styles.input} />
      </div>

      <div style={{ marginTop: 8 }} className="actionButtons">
        <button className="button" onClick={save} disabled={saving}>Save Changes</button>
        <button className="deleteButton" onClick={remove}>Delete Product</button>
      </div>
    </div>
  );
}

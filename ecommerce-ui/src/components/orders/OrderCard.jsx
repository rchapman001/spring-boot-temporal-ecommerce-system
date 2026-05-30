import React from "react";
import styles from "../styles/AdminPage.module.css";

export default function OrderCard({ order, onAction }) {
  return (
    <div className={styles.productCard}>
      <div className={styles.field}>
        <label className={styles.label}>Order ID</label>
        <input value={order.id} disabled className={styles.input} />
      </div>

      <div className={styles.field}>
        <label className={styles.label}>User ID</label>
        <input value={order.userId} disabled className={styles.input} />
      </div>

      <div className={styles.field}>
        <label className={styles.label}>Product ID</label>
        <input value={order.productId} disabled className={styles.input} />
      </div>

      <div className={styles.field}>
        <label className={styles.label}>Quantity</label>
        <input value={order.quantity} disabled className={styles.input} />
      </div>

      <div className={styles.field}>
        <label className={styles.label}>Price</label>
        <input value={`$${Number(order.price).toFixed(2)}`} disabled className={styles.input} />
      </div>

      <div className={styles.field}>
        <label className={styles.label}>Status</label>
        <input value={order.status} disabled className={styles.input} />
      </div>

      <div className="actionButtons">
        <button onClick={() => onAction(order.id, "COMPLETED")} style={{ background: "#16a34a" }} className="button">Complete Order</button>
        <button onClick={() => onAction(order.id, "CANCELLED")} style={{ background: "#dc2626" }} className="button">Cancel Order</button>
      </div>
    </div>
  );
}

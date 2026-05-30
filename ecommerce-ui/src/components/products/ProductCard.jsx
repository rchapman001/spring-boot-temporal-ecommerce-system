import React from "react";
import styles from "../styles/ProductPage.module.css";

export default function ProductCard({ product, inventory, onOrder }) {
  const availableQty = inventory ? inventory.quantityAvailable - inventory.quantityReserved : 0;
  const inStock = availableQty > 0;

  return (
    <div key={product.id} className={styles.card}>
      <h2 className={styles.name}>{product.name}</h2>
      <p className={styles.description}>{product.description}</p>
      <div className={styles.price}>${Number(product.price || 0).toFixed(2)}</div>
      <div className={styles.inventory}>
        {inventory ? (
          <>
            <span>
              Available: <strong> {availableQty}</strong>
            </span>
            <span className={inStock ? styles.inStock : styles.outOfStock}>
              {inStock ? "In Stock" : "Out of Stock"}
            </span>
          </>
        ) : (
          <span className={styles.noInventory}>No inventory data</span>
        )}
      </div>
      <button className={styles.orderButton} disabled={!inStock} onClick={() => onOrder(product)}>
        Order Product
      </button>
    </div>
  );
}

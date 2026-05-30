import React from "react";
import ProductCard from "./ProductCard";
import styles from "../styles/ProductPage.module.css";

export default function ProductList({ products, inventoryMap, onOrder }) {
  return (
    <div className={styles.grid}>
      {products.map((product) => (
        <ProductCard key={product.id} product={product} inventory={inventoryMap[product.id]} onOrder={onOrder} />
      ))}
    </div>
  );
}

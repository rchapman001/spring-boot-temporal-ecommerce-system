import { useEffect, useState } from "react";
import axios from "axios";

import common from "../components/styles/Common.module.css";
import admin from "../components/styles/AdminPage.module.css";
import { ORDER_API, DELETE_WORKFLOW_NAME, UPDATE_ORDER_STATUS_WORKFLOW } from "../api";
import * as productService from "../services/productService";
import * as inventoryService from "../services/inventoryService";
import { startWorkflow } from "../services/orderService";
import ProductForm from "../components/products/ProductForm";
import ProductEditor from "../components/products/ProductEditor";
import OrderList from "../components/orders/OrderList";
import LoadingSpinner from "../components/common/LoadingSpinner";
import ErrorMessage from "../components/common/ErrorMessage";
import toast from "react-hot-toast";

export default function AdminPage() {

  const [products, setProducts] = useState([]);
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    loadProducts();
    loadOrders();
  }, []);

  const loadProducts = async () => {
    try {
      const productsData = await productService.getProducts();

      const productsWithInventory = await Promise.all(
        productsData.map(async (product) => {
          try {
            const inv = await inventoryService.getInventoryByProduct(product.id);
            return {
              ...product,
              quantityAvailable: inv.quantityAvailable,
              quantityReserved: inv.quantityReserved,
            };
          } catch (err) {
            return {
              ...product,
              quantityAvailable: 0,
              quantityReserved: 0,
            };
          }
        })
      );

      setProducts(productsWithInventory);
    } catch (err) {
      console.error(err);
      setError("Failed to load products.");
    }
  };

  const loadOrders = async () => {
    try {
      const response = await axios.get(ORDER_API);
      const createdOrders = response.data.filter((order) => order.status === "CREATED");
      setOrders(createdOrders);
    } catch (err) {
      console.error("LOAD ORDERS ERROR:", err);
    }
  };

  // Product creation/updating is handled by feature components

  const deleteProduct = async (productId) => {
    const confirmed = window.confirm("Are you sure you want to delete this product?");
    if (!confirmed) return;

    try {
      const payload = { productId };
      await startWorkflow(DELETE_WORKFLOW_NAME, payload);
      toast.success("Product deleted successfully!");
      loadProducts();
    } catch (err) {
      console.error("DELETE WORKFLOW ERROR:", err);
      toast.error(err?.response?.data?.message || err?.message || "Failed to delete product.");
    }
  };

  const updateOrderStatus = async (
    orderId,
    status
  ) => {
    try {
      const payload = { orderId, status };
      await startWorkflow(UPDATE_ORDER_STATUS_WORKFLOW, payload);
      toast.success(`Order ${status.toLowerCase()} successfully!`);
      loadOrders();
      loadProducts();
    } catch (err) {
      console.error("ORDER STATUS ERROR:", err);
      toast.error(err?.response?.data?.message || err?.message || "Failed to update order.");
    }
  };

  return (
    <div>
      <div className={common.card}>
        <ProductForm onCreated={loadProducts} />
      </div>

      <div className={common.card}>
        <h1 className={common.title}>Manage Products</h1>
        {products.map((product) => (
          <ProductEditor key={product.id} product={product} onSaved={loadProducts} onDeleted={deleteProduct} />
        ))}
      </div>

      <div className={common.card}>
        <h1 className={common.title}>Manage Orders</h1>
        <OrderList orders={orders} onAction={updateOrderStatus} />
      </div>
    </div>
  );
}
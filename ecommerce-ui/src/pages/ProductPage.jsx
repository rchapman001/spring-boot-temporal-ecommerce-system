import { useState, useEffect } from "react";
import common from "../components/styles/Common.module.css";
import styles from "../components/styles/ProductPage.module.css";
import { getProducts } from "../services/productService";
import { getInventory } from "../services/inventoryService";
import { ORDER_WORKFLOW_NAME } from "../api";
import { startWorkflow } from "../services/orderService";
import ProductList from "../components/products/ProductList";
import Modal from "../components/common/Modal";
import toast from "react-hot-toast";
import LoadingSpinner from "../components/common/LoadingSpinner";
import ErrorMessage from "../components/common/ErrorMessage";

export default function ProductPage() {
  const [products, setProducts] = useState([]);
  const [inventoryMap, setInventoryMap] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const load = async () => {
    setLoading(true);
    setError("");
    try {
      const [productsData, inventoryData] = await Promise.all([getProducts(), getInventory()]);
      const lookup = {};
      (inventoryData || []).forEach((inv) => {
        lookup[inv.productId] = inv;
      });
      setProducts(productsData || []);
      setInventoryMap(lookup);
    } catch (err) {
      setError(err?.message || "Failed to load products.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  const refresh = load;

  const [showOrderModal, setShowOrderModal] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [email, setEmail] = useState("");
  const [submittingOrder, setSubmittingOrder] = useState(false);

  const openOrderModal = (product) => {
    setSelectedProduct(product);
    setEmail("");
    setShowOrderModal(true);
  };

  const submitOrder = async () => {
    if (!email) {
      toast.error("Email is required.");
      return;
    }

    try {
      setSubmittingOrder(true);

      const payload = {
        productId: selectedProduct.id,
        email,
        quantity: 1,
        price: selectedProduct.price,
      };

      await startWorkflow(ORDER_WORKFLOW_NAME, payload);

      toast.success("Order submitted successfully!");
      setShowOrderModal(false);
      refresh();
    } catch (err) {
      console.error("ORDER ERROR:", err);
      toast.error(err?.message || "Failed to submit order.");
    } finally {
      setSubmittingOrder(false);
    }
  };

  if (loading) return <LoadingSpinner />;
  if (error) return <ErrorMessage error={error} />;

  return (
    <div className={common.card}>
      <h1 className={common.title}>Products</h1>

      <ProductList products={products} inventoryMap={inventoryMap} onOrder={openOrderModal} />

      {showOrderModal && (
        <Modal className={{ overlay: styles.modalOverlay, modal: styles.modal }} onClose={() => setShowOrderModal(false)}>
          <h2>Order Product</h2>
          <p>
            Product: <strong> {selectedProduct?.name}</strong>
          </p>

          <input type="email" placeholder="Enter your email" value={email} onChange={(e) => setEmail(e.target.value)} className={styles.modalInput} />

          <div className={styles.modalButtons}>
            <button onClick={submitOrder} disabled={submittingOrder} className={styles.confirmButton}>
              {submittingOrder ? "Submitting..." : "Submit Order"}
            </button>

            <button onClick={() => setShowOrderModal(false)} className={`${styles.cancelButton} deleteButton`}>
              Cancel
            </button>
          </div>
        </Modal>
      )}
    </div>
  );
}
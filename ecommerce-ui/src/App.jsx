import { BrowserRouter, Link } from "react-router-dom";
import AppRoutes from "./routes";
import styles from "./components/styles/App.module.css";

function App() {
  return (
    <BrowserRouter>
      <div className={styles.layout}>
        <header className={styles.header}>
          <h2 className={styles.logo}>Ecommerce UI</h2>
          <nav>
            <Link to="/" className={styles.link}>Home</Link>
            <Link to="/products" className={styles.link}>Products</Link>
            <Link to="/admin" className={styles.link}>Admin</Link>
            <Link to="/test" className={styles.link}>Test</Link>
          </nav>
        </header>

        <main className={styles.main}>
          <AppRoutes />
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
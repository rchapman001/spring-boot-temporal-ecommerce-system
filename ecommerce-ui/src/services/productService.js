import axios from "axios";
import { PRODUCT_API } from "../api";

export async function getProducts() {
  const res = await axios.get(PRODUCT_API);
  return res.data;
}

export async function createProduct(payload) {
  const res = await axios.post(PRODUCT_API, payload);
  return res.data;
}

export async function updateProduct(id, payload) {
  const res = await axios.put(`${PRODUCT_API}/${id}`, payload);
  return res.data;
}

export async function deleteProduct(id) {
  const res = await axios.delete(`${PRODUCT_API}/${id}`);
  return res.data;
}

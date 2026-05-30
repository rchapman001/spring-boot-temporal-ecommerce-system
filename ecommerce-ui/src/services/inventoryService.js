import axios from "axios";
import { INVENTORY_API } from "../api";

export async function getInventory() {
  const res = await axios.get(INVENTORY_API);
  return res.data;
}

export async function getInventoryByProduct(productId) {
  const res = await axios.get(`${INVENTORY_API}/${productId}`);
  return res.data;
}

export async function updateInventory(productId, payload) {
  const res = await axios.put(`${INVENTORY_API}/${productId}`, payload);
  return res.data;
}

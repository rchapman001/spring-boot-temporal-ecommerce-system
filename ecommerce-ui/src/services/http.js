import axios from "axios";
import { BASE_URL } from "../api";

const http = axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Basic response interceptor for logging and centralized handling
http.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error("HTTP ERROR:", error);
    return Promise.reject(error);
  }
);

export default http;

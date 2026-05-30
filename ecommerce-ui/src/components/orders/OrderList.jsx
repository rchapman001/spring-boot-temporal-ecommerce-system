import React from "react";
import OrderCard from "./OrderCard";

export default function OrderList({ orders, onAction }) {
  if (!orders || orders.length === 0) return <p>No CREATED orders found.</p>;

  return (
    <div>
      {orders.map((order) => (
        <OrderCard key={order.id} order={order} onAction={onAction} />
      ))}
    </div>
  );
}

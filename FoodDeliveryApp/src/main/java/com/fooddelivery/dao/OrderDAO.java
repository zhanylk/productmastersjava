package com.fooddelivery.dao;

import com.fooddelivery.dto.OrderDTO;
import com.fooddelivery.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public void addOrder(OrderDTO order) {
        String sql = "INSERT INTO orders (customer_id, restaurant_id, courier_id, delivery_address, phone, status, total_amount, payment_method, payment_status, delivery_fee, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getCustomerId());
            stmt.setInt(2, order.getRestaurantId());
            stmt.setInt(3, order.getCourierId());
            stmt.setString(4, order.getDeliveryAddress());
            stmt.setString(5, order.getPhone());
            stmt.setString(6, order.getStatus());
            stmt.setDouble(7, order.getTotalAmount());
            stmt.setString(8, order.getPaymentMethod());
            stmt.setString(9, order.getPaymentStatus());
            stmt.setDouble(10, order.getDeliveryFee());
            stmt.setString(11, order.getNotes());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setRestaurantId(rs.getInt("restaurant_id"));
                order.setCourierId(rs.getInt("courier_id"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setPhone(rs.getString("phone"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setPaymentStatus(rs.getString("payment_status"));
                order.setDeliveryFee(rs.getDouble("delivery_fee"));
                order.setNotes(rs.getString("notes"));
                order.setOrderDate(rs.getString("order_date"));
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}

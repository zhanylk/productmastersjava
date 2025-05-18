package com.fooddelivery.dao;

import com.fooddelivery.dto.MenuItemDTO;
import com.fooddelivery.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    public void addMenuItem(MenuItemDTO item) {
        String sql = "INSERT INTO menu_items (restaurant_id, category_id, name, description, price, image_url, is_available, preparation_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getRestaurantId());
            stmt.setInt(2, item.getCategoryId());
            stmt.setString(3, item.getName());
            stmt.setString(4, item.getDescription());
            stmt.setDouble(5, item.getPrice());
            stmt.setString(6, item.getImageUrl());
            stmt.setBoolean(7, item.isAvailable());
            stmt.setInt(8, item.getPreparationTime());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MenuItemDTO> getAllMenuItems() {
        List<MenuItemDTO> items = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MenuItemDTO item = new MenuItemDTO();
                item.setItemId(rs.getInt("item_id"));
                item.setRestaurantId(rs.getInt("restaurant_id"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setImageUrl(rs.getString("image_url"));
                item.setAvailable(rs.getBoolean("is_available"));
                item.setPreparationTime(rs.getInt("preparation_time"));

                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}

package com.fooddelivery.dao;

import com.fooddelivery.dto.CategoryDTO;
import com.fooddelivery.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public void addCategory(CategoryDTO category) {
        String sql = "INSERT INTO categories (restaurant_id, name, description) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, category.getRestaurantId());
            stmt.setString(2, category.getName());
            stmt.setString(3, category.getDescription());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // лучше логировать
        }
    }

    public List<CategoryDTO> getCategoriesByRestaurant(int restaurantId) {
        List<CategoryDTO> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE restaurant_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CategoryDTO category = new CategoryDTO();
                category.setCategoryId(rs.getInt("category_id"));
                category.setRestaurantId(rs.getInt("restaurant_id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}

package com.fooddelivery.dao;

import com.fooddelivery.dto.RestaurantDTO;
import com.fooddelivery.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {

    // Добавление нового ресторана
    public void addRestaurant(RestaurantDTO restaurant) {
        String sql = "INSERT INTO restaurants (owner_id, name, description, cuisine_type, address, phone, opening_hours, image_url, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, restaurant.getOwnerId());
            stmt.setString(2, restaurant.getName());
            stmt.setString(3, restaurant.getDescription());
            stmt.setString(4, restaurant.getCuisineType());
            stmt.setString(5, restaurant.getAddress());
            stmt.setString(6, restaurant.getPhone());
            stmt.setString(7, restaurant.getOpeningHours());
            stmt.setString(8, restaurant.getImageUrl());
            stmt.setBoolean(9, restaurant.isActive());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Лучше заменить логированием
        }
    }

    // Получение списка всех ресторанов
    public List<RestaurantDTO> getAllRestaurants() {
        List<RestaurantDTO> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RestaurantDTO restaurant = new RestaurantDTO();
                restaurant.setRestaurantId(rs.getInt("restaurant_id"));
                restaurant.setOwnerId(rs.getInt("owner_id"));
                restaurant.setName(rs.getString("name"));
                restaurant.setDescription(rs.getString("description"));
                restaurant.setCuisineType(rs.getString("cuisine_type"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setOpeningHours(rs.getString("opening_hours"));
                restaurant.setImageUrl(rs.getString("image_url"));
                restaurant.setActive(rs.getBoolean("is_active"));
                restaurant.setCreatedAt(rs.getString("created_at"));

                restaurants.add(restaurant);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Лучше заменить логированием
        }

        return restaurants;
    }
}

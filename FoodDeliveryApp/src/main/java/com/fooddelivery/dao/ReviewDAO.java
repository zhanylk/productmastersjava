package com.fooddelivery.dao;

import com.fooddelivery.dto.ReviewDTO;
import com.fooddelivery.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ReviewDAO {
    public void addReview(ReviewDTO review) {
        String sql = "INSERT INTO reviews (order_id, user_id, restaurant_id, rating, comment) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getOrderId());
            stmt.setInt(2, review.getUserId());
            stmt.setInt(3, review.getRestaurantId());
            stmt.setInt(4, review.getRating());
            stmt.setString(5, review.getComment());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ReviewDTO> getAllReviews() {
        List<ReviewDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM reviews ORDER BY review_date DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ReviewDTO r = new ReviewDTO();
                r.setReviewId(rs.getInt("review_id"));
                r.setOrderId(rs.getInt("order_id"));
                r.setUserId(rs.getInt("user_id"));
                r.setRestaurantId(rs.getInt("restaurant_id"));
                r.setRating(rs.getInt("rating"));
                r.setComment(rs.getString("comment"));
                r.setReviewDate(rs.getString("review_date"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
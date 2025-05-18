package com.fooddelivery.servlet;

import com.fooddelivery.dao.ReviewDAO;
import com.fooddelivery.dto.ReviewDTO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {
    private ReviewDAO reviewDAO;

    @Override
    public void init() {
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ReviewDTO> reviews = reviewDAO.getAllReviews();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Reviews</title></head><body>");
        out.println("<h2>Add Review</h2>");
        out.println("<form method='POST' action='reviews'>");
        out.println("Order ID: <input type='number' name='order_id' required><br>");
        out.println("User ID: <input type='number' name='user_id' required><br>");
        out.println("Restaurant ID: <input type='number' name='restaurant_id' required><br>");
        out.println("Rating (1-5): <input type='number' name='rating' min='1' max='5' required><br>");
        out.println("Comment: <input type='text' name='comment'><br>");
        out.println("<input type='submit' value='Submit Review'>");
        out.println("</form><br><br>");

        out.println("<h2>All Reviews</h2>");
        out.println("<table border='1'><tr><th>ID</th><th>Order</th><th>User</th><th>Restaurant</th><th>Rating</th><th>Comment</th><th>Date</th></tr>");

        for (ReviewDTO r : reviews) {
            out.println("<tr><td>" + r.getReviewId() + "</td><td>" + r.getOrderId() + "</td><td>" + r.getUserId()
                    + "</td><td>" + r.getRestaurantId() + "</td><td>" + r.getRating() + "</td><td>" + r.getComment()
                    + "</td><td>" + r.getReviewDate() + "</td></tr>");
        }
        out.println("</table>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("order_id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int restaurantId = Integer.parseInt(request.getParameter("restaurant_id"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        ReviewDTO review = new ReviewDTO();
        review.setOrderId(orderId);
        review.setUserId(userId);
        review.setRestaurantId(restaurantId);
        review.setRating(rating);
        review.setComment(comment);

        reviewDAO.addReview(review);
        response.sendRedirect("reviews");
    }
}

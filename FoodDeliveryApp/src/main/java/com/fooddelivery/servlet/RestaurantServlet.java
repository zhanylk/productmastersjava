package com.fooddelivery.servlet;

import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.dto.RestaurantDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/restaurants")
public class RestaurantServlet extends HttpServlet {

    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
        restaurantDAO = new RestaurantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<RestaurantDTO> restaurants = restaurantDAO.getAllRestaurants();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Restaurants</title></head><body>");
        out.println("<h2>Add New Restaurant</h2>");
        out.println("<form method='POST' action='restaurants'>");
        out.println("Owner ID: <input type='number' name='owner_id' required><br>");
        out.println("Name: <input type='text' name='name' required><br>");
        out.println("Description: <input type='text' name='description'><br>");
        out.println("Cuisine Type: <input type='text' name='cuisine_type'><br>");
        out.println("Address: <input type='text' name='address' required><br>");
        out.println("Phone: <input type='text' name='phone' required><br>");
        out.println("Opening Hours: <input type='text' name='opening_hours'><br>");
        out.println("Image URL: <input type='text' name='image_url'><br>");
        out.println("Active: <input type='checkbox' name='is_active' checked><br>");
        out.println("<input type='submit' value='Add Restaurant'>");
        out.println("</form><br><br>");

        out.println("<h2>List of Restaurants</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Address</th><th>Phone</th><th>Active</th></tr>");

        for (RestaurantDTO restaurant : restaurants) {
            out.println("<tr>");
            out.println("<td>" + restaurant.getRestaurantId() + "</td>");
            out.println("<td>" + restaurant.getName() + "</td>");
            out.println("<td>" + restaurant.getAddress() + "</td>");
            out.println("<td>" + restaurant.getPhone() + "</td>");
            out.println("<td>" + (restaurant.isActive() ? "Yes" : "No") + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int ownerId = Integer.parseInt(request.getParameter("owner_id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String cuisineType = request.getParameter("cuisine_type");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String openingHours = request.getParameter("opening_hours");
        String imageUrl = request.getParameter("image_url");
        boolean isActive = request.getParameter("is_active") != null;

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setOwnerId(ownerId);
        restaurant.setName(name);
        restaurant.setDescription(description);
        restaurant.setCuisineType(cuisineType);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        restaurant.setOpeningHours(openingHours);
        restaurant.setImageUrl(imageUrl);
        restaurant.setActive(isActive);

        restaurantDAO.addRestaurant(restaurant);

        response.sendRedirect(request.getContextPath() + "/restaurants");
    }
}

package com.fooddelivery.servlet;

import com.fooddelivery.dao.MenuItemDAO;
import com.fooddelivery.dto.MenuItemDTO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/menu")
public class MenuItemServlet extends HttpServlet {

    private MenuItemDAO menuItemDAO;

    @Override
    public void init() {
        menuItemDAO = new MenuItemDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<MenuItemDTO> items = menuItemDAO.getAllMenuItems();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Menu</title></head><body>");
        out.println("<h2>Add Menu Item</h2>");
        out.println("<form method='POST' action='menu'>");
        out.println("Restaurant ID: <input type='number' name='restaurant_id' required><br>");
        out.println("Category ID: <input type='number' name='category_id' required><br>");
        out.println("Name: <input type='text' name='name' required><br>");
        out.println("Description: <input type='text' name='description'><br>");
        out.println("Price: <input type='number' step='0.01' name='price' required><br>");
        out.println("Image URL: <input type='text' name='image_url'><br>");
        out.println("Is Available: <input type='checkbox' name='is_available'><br>");
        out.println("Preparation Time (min): <input type='number' name='preparation_time'><br>");
        out.println("<input type='submit' value='Add Menu Item'>");
        out.println("</form><br><br>");

        out.println("<h2>Menu Items</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Description</th><th>Price</th><th>Available</th><th>Prep Time</th></tr>");

        for (MenuItemDTO item : items) {
            out.println("<tr>");
            out.println("<td>" + item.getItemId() + "</td>");
            out.println("<td>" + item.getName() + "</td>");
            out.println("<td>" + item.getDescription() + "</td>");
            out.println("<td>" + item.getPrice() + "</td>");
            out.println("<td>" + (item.isAvailable() ? "Yes" : "No") + "</td>");
            out.println("<td>" + item.getPreparationTime() + " min</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MenuItemDTO item = new MenuItemDTO();
        item.setRestaurantId(Integer.parseInt(request.getParameter("restaurant_id")));
        item.setCategoryId(Integer.parseInt(request.getParameter("category_id")));
        item.setName(request.getParameter("name"));
        item.setDescription(request.getParameter("description"));
        item.setPrice(Double.parseDouble(request.getParameter("price")));
        item.setImageUrl(request.getParameter("image_url"));
        item.setAvailable(request.getParameter("is_available") != null);
        item.setPreparationTime(Integer.parseInt(request.getParameter("preparation_time")));

        menuItemDAO.addMenuItem(item);

        response.sendRedirect("menu");
    }
}

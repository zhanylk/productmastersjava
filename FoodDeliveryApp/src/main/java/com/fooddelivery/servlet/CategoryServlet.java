package com.fooddelivery.servlet;

import com.fooddelivery.dao.CategoryDAO;
import com.fooddelivery.dto.CategoryDTO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {

    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String restaurantIdParam = request.getParameter("restaurant_id");

        int restaurantId;
        if (restaurantIdParam != null && !restaurantIdParam.isEmpty()) {
            restaurantId = Integer.parseInt(restaurantIdParam);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Restaurant ID is required in the query string.");
            return;
        }

        List<CategoryDTO> categories = categoryDAO.getCategoriesByRestaurant(restaurantId);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Categories</title></head><body>");
        out.println("<h2>Add Category</h2>");
        out.println("<form method='POST' action='categories'>");
        out.println("<input type='hidden' name='restaurant_id' value='" + restaurantId + "'>");
        out.println("Name: <input type='text' name='name' required><br>");
        out.println("Description: <input type='text' name='description'><br>");
        out.println("<input type='submit' value='Add Category'>");
        out.println("</form><br><br>");

        out.println("<h2>Categories for Restaurant ID: " + restaurantId + "</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Description</th></tr>");

        for (CategoryDTO category : categories) {
            out.println("<tr>");
            out.println("<td>" + category.getCategoryId() + "</td>");
            out.println("<td>" + category.getName() + "</td>");
            out.println("<td>" + category.getDescription() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int restaurantId = Integer.parseInt(request.getParameter("restaurant_id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        CategoryDTO category = new CategoryDTO();
        category.setRestaurantId(restaurantId);
        category.setName(name);
        category.setDescription(description);

        categoryDAO.addCategory(category);

        response.sendRedirect("categories?restaurant_id=" + restaurantId);
    }
}

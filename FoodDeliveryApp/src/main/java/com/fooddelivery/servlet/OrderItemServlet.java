package com.fooddelivery.servlet;

import com.fooddelivery.dao.OrderItemDAO;
import com.fooddelivery.dto.OrderItemDTO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/order-items")
public class OrderItemServlet extends HttpServlet {

    private OrderItemDAO orderItemDAO;

    @Override
    public void init() {
        orderItemDAO = new OrderItemDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(request.getParameter("order_id"));
        List<OrderItemDTO> items = orderItemDAO.getOrderItemsByOrderId(orderId);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Order Items</title></head><body>");
        out.println("<h2>Add Order Item</h2>");
        out.println("<form method='POST' action='order-items'>");
        out.println("Order ID: <input type='number' name='order_id' required><br>");
        out.println("Menu Item ID: <input type='number' name='menu_item_id' required><br>");
        out.println("Quantity: <input type='number' name='quantity' required><br>");
        out.println("Price: <input type='number' step='0.01' name='price' required><br>");
        out.println("Special Requests: <input type='text' name='special_requests'><br>");
        out.println("<input type='submit' value='Add Order Item'>");
        out.println("</form><br><br>");

        out.println("<h2>Order Items</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Menu Item ID</th><th>Quantity</th><th>Price</th><th>Requests</th></tr>");
        for (OrderItemDTO item : items) {
            out.println("<tr>");
            out.println("<td>" + item.getOrderItemId() + "</td>");
            out.println("<td>" + item.getMenuItemId() + "</td>");
            out.println("<td>" + item.getQuantity() + "</td>");
            out.println("<td>" + item.getPrice() + "</td>");
            out.println("<td>" + item.getSpecialRequests() + "</td>");
            out.println("</tr>");
        }
        out.println("</table></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderItemDTO item = new OrderItemDTO();
        item.setOrderId(Integer.parseInt(request.getParameter("order_id")));
        item.setMenuItemId(Integer.parseInt(request.getParameter("menu_item_id")));
        item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        item.setPrice(Double.parseDouble(request.getParameter("price")));
        item.setSpecialRequests(request.getParameter("special_requests"));

        orderItemDAO.addOrderItem(item);

        response.sendRedirect("order-items?order_id=" + item.getOrderId());
    }
}

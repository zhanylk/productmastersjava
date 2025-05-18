package com.fooddelivery.servlet;

import com.fooddelivery.dao.OrderDAO;
import com.fooddelivery.dto.OrderDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<OrderDTO> orders = orderDAO.getAllOrders();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Orders</title></head><body>");
        out.println("<h2>Add Order</h2>");
        out.println("<form method='POST' action='orders'>");
        out.println("Customer ID: <input type='number' name='customer_id' required><br>");
        out.println("Restaurant ID: <input type='number' name='restaurant_id' required><br>");
        out.println("Courier ID: <input type='number' name='courier_id'><br>");
        out.println("Delivery Address: <input type='text' name='delivery_address' required><br>");
        out.println("Phone: <input type='text' name='phone' required><br>");
        out.println("Status: <input type='text' name='status' value='PENDING'><br>");
        out.println("Total Amount: <input type='number' step='0.01' name='total_amount' required><br>");
        out.println("Payment Method: <input type='text' name='payment_method' value='CASH'><br>");
        out.println("Payment Status: <input type='text' name='payment_status' value='PENDING'><br>");
        out.println("Delivery Fee: <input type='number' step='0.01' name='delivery_fee'><br>");
        out.println("Notes: <input type='text' name='notes'><br>");
        out.println("<input type='submit' value='Add Order'>");
        out.println("</form><br><br>");

        out.println("<h2>List of Orders</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Customer</th><th>Restaurant</th><th>Status</th><th>Amount</th></tr>");

        for (OrderDTO order : orders) {
            out.println("<tr>");
            out.println("<td>" + order.getOrderId() + "</td>");
            out.println("<td>" + order.getCustomerId() + "</td>");
            out.println("<td>" + order.getRestaurantId() + "</td>");
            out.println("<td>" + order.getStatus() + "</td>");
            out.println("<td>" + order.getTotalAmount() + "</td>");
            out.println("</tr>");
        }

        out.println("</table></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderDTO order = new OrderDTO();
        order.setCustomerId(Integer.parseInt(request.getParameter("customer_id")));
        order.setRestaurantId(Integer.parseInt(request.getParameter("restaurant_id")));
        order.setCourierId(Integer.parseInt(request.getParameter("courier_id")));
        order.setDeliveryAddress(request.getParameter("delivery_address"));
        order.setPhone(request.getParameter("phone"));
        order.setStatus(request.getParameter("status"));
        order.setTotalAmount(Double.parseDouble(request.getParameter("total_amount")));
        order.setPaymentMethod(request.getParameter("payment_method"));
        order.setPaymentStatus(request.getParameter("payment_status"));
        order.setDeliveryFee(Double.parseDouble(request.getParameter("delivery_fee")));
        order.setNotes(request.getParameter("notes"));

        orderDAO.addOrder(order);

        response.sendRedirect("orders");
    }
}

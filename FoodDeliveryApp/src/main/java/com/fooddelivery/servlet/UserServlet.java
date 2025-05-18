package com.fooddelivery.servlet;

import com.fooddelivery.dao.UserDAO;
import com.fooddelivery.dto.UserDTO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<UserDTO> users = userDAO.getAllUsers();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Users</title></head><body>");
        out.println("<h2>Add New User</h2>");
        out.println("<form method='POST' action='users'>");
        out.println("Username: <input type='text' name='username' required><br>");
        out.println("Password: <input type='password' name='password' required><br>");
        out.println("Email: <input type='email' name='email' required><br>");
        out.println("Phone: <input type='text' name='phone' required><br>");
        out.println("Role: <select name='role'>");
        out.println("<option value='CUSTOMER'>Customer</option>");
        out.println("<option value='RESTAURANT_OWNER'>Restaurant Owner</option>");
        out.println("<option value='COURIER'>Courier</option>");
        out.println("<option value='ADMIN'>Admin</option>");
        out.println("</select><br>");
        out.println("<input type='submit' value='Add User'>");
        out.println("</form><br><br>");

        out.println("<h2>List of Users</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Username</th><th>Email</th><th>Phone</th><th>Role</th></tr>");

        for (UserDTO user : users) {
            out.println("<tr>");
            out.println("<td>" + user.getUserId() + "</td>");
            out.println("<td>" + user.getUsername() + "</td>");
            out.println("<td>" + user.getEmail() + "</td>");
            out.println("<td>" + user.getPhone() + "</td>");
            out.println("<td>" + user.getRole() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String role = request.getParameter("role");

        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRole(role);

        userDAO.addUser(user);

        response.sendRedirect(request.getContextPath() + "/users");
    }
}
